package com.bom.newsfeed.global.security;

import static com.bom.newsfeed.global.common.constant.ErrorCode.*;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Jwt 검증 실패")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.info("로그인 인증 실패");
		setResponseConfig(response);
		objectMapper
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.writeValue(response.getWriter(),
				ErrorResponse.builder()
					.statusCode(INVALID_AUTH_TOKEN.getHttpStatus().value())
					.message(INVALID_AUTH_TOKEN.getDetail())
					.build()
			);
	}

	private void setResponseConfig(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.setStatus(INVALID_AUTH_TOKEN.getHttpStatus().value());
	}
}
