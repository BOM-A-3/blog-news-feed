package com.bom.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogNewsFeedApplication {
		static {
			System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		}
	public static void main(String[] args) {
		SpringApplication.run(BlogNewsFeedApplication.class, args);
	}
}
