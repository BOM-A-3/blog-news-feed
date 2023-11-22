package com.bom.newsfeed.global.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bom.newsfeed.global.security.UserDetailsImpl;

@EnableJpaAuditing
@Configuration
public class AuditingConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //SecurityContextHolder에 저장된 인증정보  가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //인증이 안되있거나 익명인 경우 빈값 반환
        if (null == authentication || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        //인증된 유저 정보 가지요기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //유저 Id 반환
        return Optional.ofNullable(userDetails.getUsername());
    }
}
