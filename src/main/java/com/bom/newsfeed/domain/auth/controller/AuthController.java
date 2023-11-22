package com.bom.newsfeed.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.auth.service.AuthService;
import com.bom.newsfeed.global.common.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "회원 인증 API", description = "회원 인증 API 컨트롤러")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Operation(
		summary = "토큰 재발급",
		description = "토큰 재발급 API",
		parameters = {
			@Parameter(name = "RefreshToken", description = "Jwt RefreshToken", in = ParameterIn.COOKIE)
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "204",
			description = "토큰 재발급 성공"
		),
		@ApiResponse(
			responseCode = "400",
			description = "유효하지 않은 토큰으로 재발급 요청한 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PostMapping("/reissue")
	public ResponseEntity<Object> reissue(HttpServletRequest request, HttpServletResponse response) {
		authService.reissue(request, response);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


	@Operation(
		summary = "로그아웃",
		description = "로그아웃 API",
		parameters = {
			@Parameter(name = "Authorization", description = "Jwt AccessToken", in = ParameterIn.HEADER)
		}
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "204",
			description = "로그아웃 성공"
		),
		@ApiResponse(
			responseCode = "400",
			description = "유효하지 않은 토큰으로 로그아웃 요청한 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@DeleteMapping("/logout")
	public void logout(HttpServletRequest request) {
		authService.logout(request);
	}

}
