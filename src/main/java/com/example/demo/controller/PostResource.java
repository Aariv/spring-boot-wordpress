/**
 * 
 */
package com.example.demo.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.example.demo.property.WordPressUtils;
import com.example.demo.service.FileStorageService;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api")
public class PostResource {

	@Value("${wordpress.baseurl}")
	private String baseURL;
	
	@Value("${wordpress.username}")
	private String username;
	
	@Value("${wordpress.password}")
	private String password;
	
	@Value("${wordpress.debug}")
	private boolean debug;
	
	@Value("${wordpress.permlink}")
	private boolean permaLink;
	
	@Value("${wordpress.posts.url}")
	private String postsURL;
	
	private final Wordpress client;

	@Autowired
	FileStorageService fileStorageService;

	public PostResource() {
		this.client = ClientFactory.fromConfig(ClientConfig.of(WordPressUtils.BASE_URL, WordPressUtils.USERNAME, WordPressUtils.PASSWORD, WordPressUtils.permLink, WordPressUtils.debug));
	}

	@GetMapping(value = "/posts")
	public PagedResponse<Post> listPosts() {
		
		PagedResponse<Post> response;
		try {
			response = client.getPagedResponse(new URI(postsURL), Post.class);
			return response;
		} catch (Exception e) {
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
