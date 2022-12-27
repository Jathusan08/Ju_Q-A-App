package com.JU.QuestionAndAnswer_App.mapper;

import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Post;

public class PostMapper {

	
	// convert Post entity to PostDto
	public static PostDto mapToPostDto(Post post) {
		
		PostDto postDto = PostDto.builder()
				.id(post.getId())
				.title(post.getTitle())
				.url(post.getUrl())
				.content(post.getContent())
				.shortDescription(post.getShortDescription())
				.dateCreated(post.getDateCreated())
				.lastUpdated(post.getLastUpdated())
				.build();
		
		return postDto;
	}
	
	// convert PostDto to Post entity
	public static Post mapToPost( PostDto postDto) {
		
		Post post = Post.builder()
				.id(postDto.getId())
				.title(postDto.getTitle())
				.url(postDto.getUrl())
				.content(postDto.getContent())
				.shortDescription(postDto.getShortDescription())
				.dateCreated(postDto.getDateCreated())
				.lastUpdated(postDto.getLastUpdated())
				.build();
		
		return post;
		
		
	}
	
}
