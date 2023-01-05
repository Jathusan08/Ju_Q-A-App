package com.JU.QuestionAndAnswer_App.service;

import java.util.List;

import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Post;


public interface PostService {
	
	List<PostDto> getAllPosts();
	
	void createPost(PostDto postDto);
	
	PostDto findPostById(Long id);
	
	void updatePost(PostDto postDto);
	
	void deletePost(Long postId);
	
	PostDto findPostByUrl(String postUrl);
	
	List<PostDto> searchPosts(String query);
	
	List<PostDto> findPostsByUser();

}
