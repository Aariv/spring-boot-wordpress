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

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;
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
			  = restTemplate.getForEntity("http://192.168.43.44/wordpress/index.php/wp-json/wp/v2/posts", String.class);
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
		// http://178.79.168.34:82/wp-json/jwt-auth/v1/token
		
		final Wordpress client = ClientFactory.fromConfig(ClientConfig.of("http://192.168.43.44/wordpress/index.php/wp-json/wp/v2/posts", "admin", "Admin@1", true));
		
		final Post post = PostBuilder.aPost()
			    .withTitle(TitleBuilder.aTitle().withRendered("Ariv-Test").build())
			    .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("test").build())
			    .withContent(ContentBuilder.aContent().withRendered("test").build())
			    .build();

			try {
				final Post createdPost = client.createPost(post, PostStatus.publish);
				System.out.println(createdPost.toString());
			} catch (PostCreateException e) {
				e.printStackTrace();
			}
			return null;

		
		/*String plainCreds = "admin:Admin@1";
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
		ResponseEntity<WordPress> response = restTemplate.exchange("http://192.168.43.44/wordpress/index.php/wp-json/wp/v2/posts", HttpMethod.POST, request, WordPress.class);
		WordPress account = response.getBody();*/
		//return account;
	}
}
