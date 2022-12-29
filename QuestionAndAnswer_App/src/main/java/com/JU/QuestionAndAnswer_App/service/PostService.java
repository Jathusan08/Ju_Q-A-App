package com.JU.QuestionAndAnswer_App.service;

import java.util.List;

import com.JU.QuestionAndAnswer_App.dto.PostDto;


public interface PostService {
	
	List<PostDto> getAllPosts();
	
	void createPost(PostDto postDto);
	
	PostDto findPostById(Long id);
	
	void updatePost(PostDto postDto);
	
	void deletePost(Long postId);

}
