package com.JU.QuestionAndAnswer_App.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.CommentRepository;
import com.JU.QuestionAndAnswer_App.dao.PostRepository;
import com.JU.QuestionAndAnswer_App.dto.CommentDto;
import com.JU.QuestionAndAnswer_App.entity.Comment;
import com.JU.QuestionAndAnswer_App.entity.Post;
import com.JU.QuestionAndAnswer_App.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	
	
	public CommentServiceImpl() {}
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) { // injecting the dependency
		
		this.commentRepository =  commentRepository;
		this.postRepository =  postRepository;
		
	}

	@Override
	public void createComment(String postUrl, CommentDto commentDto) {
	
		// get Posts Object by postUrl
		Post post = this.postRepository.findByUrl(postUrl).get();
		
		// convert commentDto to comment entity
		
		Comment comment = CommentMapper.mapToComment(commentDto);
		
		// now let add post object to comment object where it requires an object of Post based on url
		comment.setPost(post);
		
		// let save comment object
		this.commentRepository.save(comment);
	}

}
