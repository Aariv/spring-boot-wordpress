/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.ContentDTO;
import com.example.demo.domain.WordPress;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api/admin")
public class PostAdminResource {

	private RestTemplate restTemplate;
	
	public PostAdminResource(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@GetMapping(value = "/posts")
	public JsonNode listPosts() {
		try {
			ResponseEntity<String> response
			  = restTemplate.getForEntity("https://oxygenna.com/wp-json/wp/v2/posts?_embed", String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			return root;
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/posts")
	public WordPress addNewPost() {
		String plainCreds = "admin:Admin@1";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		
		WordPress wordPress = new WordPress();
		wordPress.setType("Dev-Test");
		ContentDTO content = new ContentDTO();
		content.setRendered("http://localhost/wordpress/wp-content/uploads/2018/07/1.png");
		wordPress.setContent(content);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<WordPress> response = restTemplate.exchange("http://localhost/wordpress/index.php/wp-json/wp/v2/posts", HttpMethod.POST, request, WordPress.class);
		WordPress account = response.getBody();
		return account;
	}
}
