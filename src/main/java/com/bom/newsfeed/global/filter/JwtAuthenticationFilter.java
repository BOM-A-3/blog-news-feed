package com.bom.newsfeed.global.filter;


import static com.bom.newsfeed.global.exception.ErrorCode.*;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.global.auth.TokenDto;
import com.bom.newsfeed.global.security.UserDetailsImpl;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.LoginRequest;
import com.bom.newsfeed.global.security.UserDetailsServiceImpl;
import com.bom.newsfeed.global.util.JwtUtil;
import com.bom.newsfeed.global.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 인증")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,  RedisUtil redisUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.objectMapper = objectMapper;

        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 인증 성공");
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        MemberRole role = ((UserDetailsImpl) authentication.getPrincipal()).getMemberDto().getRole();

        TokenDto tokenDto = jwtUtil.createToken(username, role);
        jwtUtil.setTokenResponse(tokenDto, response);
        redisUtil.saveKey("RefreshToken:" + username, 24 * 60, tokenDto.getRefreshToken());
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 인증 실패");
        setResponseConfig(response);
        objectMapper
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writeValue(response.getWriter(),
                        ErrorResponse.builder()
                                .status(UNAUTHORIZED_MEMBER.getHttpStatus().value())
                                .name(UNAUTHORIZED_MEMBER.name())
                                .message(UNAUTHORIZED_MEMBER.getDetail())
                                .build()
                );
    }

    private void setResponseConfig(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
    }
}
