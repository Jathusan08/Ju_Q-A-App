package com.JU.QuestionAndAnswer_App.service;

import com.JU.QuestionAndAnswer_App.dto.CommentDto;

public interface CommentService {
	
	void createComment(String postUrl, CommentDto commentDto);

}
