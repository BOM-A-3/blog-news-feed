package com.bom.newsfeed.global.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bom.newsfeed.global.util.RedisUtil;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperty.class)
public class RedisConfig {
	private final RedisProperty redisProperty;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisProperty.getHost(), redisProperty.getPort());
	}


	@Bean
	public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);

		return template;

	}


	// 필수는 아니고 제 회사에서 쓰는 형태의 설정
	@Bean
	public RedisUtil redisComponent(StringRedisTemplate stringRedisTemplate) {

		return new RedisUtil(stringRedisTemplate);

	}

}

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.data.redis")
class RedisProperty {

	private final String host;
	private final Integer port;

}