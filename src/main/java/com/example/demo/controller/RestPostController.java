/**
 * 
 */
package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.domain.WordPress;

import reactor.core.publisher.Flux;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api")
public class RestPostController {

	WebClient webClient = null;

	public RestPostController() {
		webClient = WebClient.builder().baseUrl("https://oxygenna.com/wp-json/wp/v2")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
	}

	@GetMapping("/posts")
	public Flux<WordPress> listPosts() {
		return webClient.get().uri("/posts?_embed")
				.retrieve().bodyToFlux(WordPress.class);
	}
}
