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

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	public AuthenticationEntryPointImpl(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		setResponseConfig(response);
		objectMapper
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.writeValue(response.getWriter(),
				ErrorResponse.builder()
					.statusCode(ACCESS_DENIED.getHttpStatus().value())
					.message(ACCESS_DENIED.getDetail())
					.build()
			);
	}

	private void setResponseConfig(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.setStatus(ACCESS_DENIED.getHttpStatus().value());
	}
}
