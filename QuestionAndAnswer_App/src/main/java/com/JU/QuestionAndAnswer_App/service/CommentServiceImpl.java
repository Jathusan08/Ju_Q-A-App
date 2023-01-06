package com.JU.QuestionAndAnswer_App.service;

import java.util.ArrayList;   
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.CommentRepository;
import com.JU.QuestionAndAnswer_App.dao.PostRepository;
import com.JU.QuestionAndAnswer_App.dao.UserRepository;
import com.JU.QuestionAndAnswer_App.dto.CommentDto;
import com.JU.QuestionAndAnswer_App.entity.Comment;
import com.JU.QuestionAndAnswer_App.entity.Post;
import com.JU.QuestionAndAnswer_App.entity.User;
import com.JU.QuestionAndAnswer_App.mapper.CommentMapper;
import com.JU.QuestionAndAnswer_App.util.SecurityUtils;

@Service
public class CommentServiceImpl implements CommentService {
	
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private UserRepository userRepository;
	
	
	public CommentServiceImpl() {}
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) { // injecting the dependency
		
		this.commentRepository =  commentRepository;
		this.postRepository =  postRepository;
		this.userRepository =  userRepository;
		
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

	@Override
	public List<CommentDto> findAllComments() {
		
		List<Comment> comments = this.commentRepository.findAll();
		
		
		// convert Comment entity to CommentDto		
		List<CommentDto> commentDto  = new ArrayList<>();
		
		for(Comment comment: comments) {
			
			
			commentDto.add(CommentMapper.mapToCommentDto(comment));
		}
		
		return commentDto;
	}

	@Override
	public void deleteComment(Long commentId) {
		
		this.commentRepository.deleteById(commentId);
		
	}

	@Override
	public List<CommentDto> findCommentsByPost() {
		/// get the current logged user email
		String email = SecurityUtils.getCurrentUser().getUsername();
		
		User createdBy = this.userRepository.findByEmail(email);
		
		// now let get the useId
		Long userId = createdBy.getId();
		
		List<Comment> comments = this.commentRepository.findCommentsByPost(userId);
		
		
		// let convert comment to commentDtos
		List<CommentDto> commentDtos = new ArrayList<>();
		
		for(Comment comment : comments) {
			
			
			commentDtos.add(CommentMapper.mapToCommentDto(comment));
			
		}
		
		return commentDtos;
	}


}
