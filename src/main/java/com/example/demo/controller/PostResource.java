/**
 * 
 */
package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileStorageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api")
public class PostResource {

	private RestTemplate restTemplate;

	@Autowired
	FileStorageService fileStorageService;

	public PostResource(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@GetMapping(value = "/posts")
	public JsonNode listPosts() {
		try {
			ResponseEntity<String> response = restTemplate
					.getForEntity("https://oxygenna.com/wp-json/wp/v2/posts?_embed", String.class);
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
	public String postRequestWithFiles(@RequestParam("doc") MultipartFile doc_file,
			@RequestParam("img") MultipartFile img_file, @RequestParam("name") String name) {
		String filename1 = null, filename2 = null, temp_name = name;
		
		try {
			filename1 = fileStorageService.storeFile(doc_file);
			filename2 = fileStorageService.storeFile(img_file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/********************* Post Request with files *************************/
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("doc-file", doc_file);
		bodyMap.add("img-file", img_file);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://oxygenna.com/wp-json/wp/v2/posts?_embed",
				HttpMethod.POST, requestEntity, String.class);
		System.out.println("response status: " + response.getStatusCode());
		System.out.println("response body: " + response.getBody());

		/********************* Post Request with files *************************/

		
		return filename1 + "," + filename2 + ", name: " + temp_name;
	}

}
