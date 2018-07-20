/**
 * 
 */
package com.example.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Content;
import com.afrozaar.wordpress.wpapi.v2.model.Links;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Self;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.example.demo.property.WordPressUtils;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

/**
 * @author zentere
 *
 */
@RestController
@RequestMapping("/api/admin")
public class PostAdminResource {
	
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
	
	private Wordpress client;

	public PostAdminResource() {
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
	public Post addNewPost() throws MismatchedInputException {
		Post post = new Post();
		post.setAuthor(1L);
		Content content = new Content();
		content.setRendered("This is the content of the post");
		post.setContent(content);
		post.setStatus("publish");
		Links links = new Links();
		List<Self> self = new ArrayList<Self>();
		links.setSelf(self);
		post.setLinks(links);
		post.setModified("");
		Title title = new Title();
		title.setRendered("New Title from dev");
		post.setTitle(title);
		
		post.setType("post");
		post.setPassword("uy");
		post.setCommentStatus("closed");
		post.setPingStatus("closed");
		List<Long> categoryIds = new ArrayList<>();
		post.setCategoryIds(categoryIds);
		
		try {
			Post createdPost = client.createPost(post, PostStatus.draft);
			return createdPost;
		} catch (PostCreateException e) {
			e.printStackTrace();
		}
		return null;
	}
}
