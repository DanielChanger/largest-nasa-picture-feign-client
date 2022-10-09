package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableFeignClients
@SpringBootApplication
public class LargestNasaPictureFeignClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LargestNasaPictureFeignClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(List<HttpMessageConverter<?>> converters) {
		return new RestTemplate(converters);
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}
}
