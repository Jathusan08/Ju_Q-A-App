package com.JU.QuestionAndAnswer_App.service;

import java.util.List; 

import com.JU.QuestionAndAnswer_App.dto.CommentDto;

public interface CommentService {
	
	void createComment(String postUrl, CommentDto commentDto);

	List<CommentDto> findAllComments();

	void deleteComment(Long commentId);
	
	List<CommentDto> findCommentsByPost();

}
