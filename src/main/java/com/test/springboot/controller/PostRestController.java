package com.test.springboot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.springboot.exception.ResourceNotFoundException;
import com.test.springboot.model.Post;
import com.test.springboot.repository.PostRepository;

/**
 * @author Manas Mohanty
 * 
 * The Class PostRestController.
 */
@RestController
@RequestMapping("/test-api")
public class PostRestController {

	/** The post repository. */
	@Autowired
	PostRepository postRepository;

	/**
	 * Gets the all posts.
	 *
	 * @param pageable the pageable
	 * @return the all posts
	 */
	@GetMapping("/posts")
	public Page<Post> getAllPosts(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

	/**
	 * Gets the post.
	 *
	 * @param postId the post id
	 * @return the post
	 */
	@GetMapping("/posts/{postId}")
	public Post getPost(@PathVariable Long postId) {

		Optional<Post> post = postRepository.findById(postId);

		if (!post.isPresent())
			throw new ResourceNotFoundException("PostId " + postId + " not found");
		return post.get();
	}

	/**
	 * Creates the post.
	 *
	 * @param post the post
	 * @return the post
	 */
	@PostMapping("/posts")
	public Post createPost(@Valid @RequestBody Post post) {
		
		
		
		return postRepository.save(post);
	}

	/**
	 * Update post.
	 *
	 * @param postId the post id
	 * @param postRequest the post request
	 * @return the post
	 */
	@PutMapping("/posts/{postId}")
	public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
		return postRepository.findById(postId).map(post -> {
			post.setPostTitle(postRequest.getPostTitle());
			post.setPostDescription(postRequest.getPostDescription());
			post.setPostContent(postRequest.getPostDescription());
			return postRepository.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	}

	/**
	 * Delete post.
	 *
	 * @param postId the post id
	 * @return the response entity
	 */
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		return postRepository.findById(postId).map(post -> {
			postRepository.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	}

}
