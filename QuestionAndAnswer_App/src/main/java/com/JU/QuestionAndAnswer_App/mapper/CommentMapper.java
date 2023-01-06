package com.JU.QuestionAndAnswer_App.mapper;

import com.JU.QuestionAndAnswer_App.dto.CommentDto; 
import com.JU.QuestionAndAnswer_App.entity.Comment;

public class CommentMapper {
	
	// convert Comment entity to CommentDto
		public static CommentDto mapToCommentDto(Comment comment) {
			
			CommentDto commentDto = CommentDto.builder()
					.id(comment.getId())
					.name(comment.getName())
					.email(comment.getEmail())
					.content(comment.getContent())
					.dateCreated(comment.getDateCreated())
					.lastUpdated(comment.getLastUpdated())
					.build();
			
			return commentDto;
			
		}
		
		
		// convert Comment entity to CommentDto
				public static Comment mapToComment(CommentDto commentDto) {
					
					Comment comment = Comment.builder()
							.id(commentDto.getId())
							.name(commentDto.getName())
							.email(commentDto.getEmail())
							.content(commentDto.getContent())
							.dateCreated(commentDto.getDateCreated())
							.lastUpdated(commentDto.getLastUpdated())
							.build();
					
					return comment;
					
				}

}
