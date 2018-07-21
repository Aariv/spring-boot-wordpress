/**
 * 
 */
package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.example.demo.service.FileStorageService;
import com.example.demo.utils.WordPressUtils;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api/admin")
public class PostAdminResource {

	private Wordpress client;

	@Autowired
	FileStorageService fileStorageService;

	public PostAdminResource() {
		this.client = ClientFactory.fromConfig(ClientConfig.of(WordPressUtils.BASE_URL, WordPressUtils.USERNAME,
				WordPressUtils.PASSWORD, WordPressUtils.permLink, WordPressUtils.debug));
	}

	@GetMapping(value = "/posts")
	public PagedResponse<Post> listPosts() {
		PagedResponse<Post> response;
		try {
			response = client.getPagedResponse(new URI(WordPressUtils.POSTS_URL), Post.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/posts")
	public Post addNewPost(@RequestBody Post post) throws MismatchedInputException {
		try {
			Post createdPost = client.createPost(post, PostStatus.publish);
			return createdPost;
		} catch (PostCreateException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	@PostMapping(value = "/draftPosts")
	public Post addNewPostWithDraft(@RequestBody Post post) throws MismatchedInputException {
		try {
			Post createdPost = client.createPost(post, PostStatus.draft);
			return createdPost;
		} catch (PostCreateException e) {
			e.printStackTrace();
		}
		return post;
	}

	@PostMapping("/upload")
	public List<String> postRequestWithFiles(@RequestParam("doc") MultipartFile[] doc_file) {
		List<String> filenames = null;
		try {
			filenames = fileStorageService.storeFile(doc_file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filenames;
	}
}