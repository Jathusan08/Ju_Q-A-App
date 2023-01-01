package com.JU.QuestionAndAnswer_App.mapper;

import java.util.HashSet;
import java.util.Set;

import com.JU.QuestionAndAnswer_App.dto.CommentDto;
import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Comment;
import com.JU.QuestionAndAnswer_App.entity.Post;

public class PostMapper {

	
	// convert Post entity to PostDto
	public static PostDto mapToPostDto(Post post) {
		
		// let convert set of comments to set of commentDtos
		
		Set<CommentDto> commentDtos = new HashSet<>();
		
		
		for (Comment comment : post.getComments()) {
            
			commentDtos.add(CommentMapper.mapToCommentDto(comment));
        }
	
		
		PostDto postDto = PostDto.builder()
				.id(post.getId())
				.title(post.getTitle())
				.url(post.getUrl())
				.content(post.getContent())
				.shortDescription(post.getShortDescription())
				.dateCreated(post.getDateCreated())
				.lastUpdated(post.getLastUpdated())
				.comments(commentDtos)
				.build();
		
		return postDto;
	}
	
	// convert PostDto to Post entity
	public static Post mapToPost( PostDto postDto) {
		
		
		
		// let convert set of commentDTos to set of comment entity
		
		Set<Comment> comments = new HashSet<>();
		
		for (CommentDto commentDto : postDto.getComments()) {
       
			comments.add(CommentMapper.mapToComment(commentDto));
        }
		
		
		Post post = Post.builder()
				.id(postDto.getId())
				.title(postDto.getTitle())
				.url(postDto.getUrl())
				.content(postDto.getContent())
				.shortDescription(postDto.getShortDescription())
				.dateCreated(postDto.getDateCreated())
				.lastUpdated(postDto.getLastUpdated())
				.comments(comments)
				.build();
		
		return post;
		
		
	}
	
}
