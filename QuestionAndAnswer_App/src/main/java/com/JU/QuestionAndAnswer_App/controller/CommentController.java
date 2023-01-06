package com.JU.QuestionAndAnswer_App.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.JU.QuestionAndAnswer_App.dto.CommentDto;
import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.service.CommentService;
import com.JU.QuestionAndAnswer_App.service.PostService;

import jakarta.validation.Valid;


@Controller
public class CommentController {

	
	private CommentService commentService;
	private PostService postService;
	
	public CommentController() {}
	
	@Autowired
	public CommentController(CommentService commentService, PostService postService) {
		
		this.commentService = commentService;
		this.postService=postService;
		
	}
	
	
	// submit a comment for a specific QA Post
	
	@PostMapping("/{postUrl}/comments")
	public String createComment(@PathVariable("postUrl") String postUrl, 
								@Valid @ModelAttribute("commentDto")CommentDto commentDto, 
								BindingResult result, 
								Model model) {
		
		PostDto postdto = this.postService.findPostByUrl(postUrl);
		
		if(result.hasErrors()) {
			
			model.addAttribute("post", postdto);
			
			//i'm passing the same model object of the user input when there is error
			model.addAttribute("commentDto", commentDto);
			
			return "QA/View_post_For_Client";
		}
		
		
		this.commentService.createComment(postUrl, commentDto);
		
		return "redirect:/post/"+postUrl;
	}
	
}
