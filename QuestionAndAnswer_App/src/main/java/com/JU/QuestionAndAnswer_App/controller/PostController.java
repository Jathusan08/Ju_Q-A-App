package com.JU.QuestionAndAnswer_App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.JU.QuestionAndAnswer_App.service.PostService;

@Controller
public class PostController {
	
	private PostService postService;
	
	public PostController() {
		
		
	}
	
	@Autowired
public PostController(PostService postService) {
		
		this.postService = postService;
		
	}
	
	
	// get list of posts for Admin Users ONLY
	@GetMapping("/admin/posts")
	public String showAllPosts(Model model) {
		
		// get the list of posts and add to the model so we can use that attribute to populate the data
		model.addAttribute("ListOfPosts", this.postService.getAllPosts());
		
		return "Posts_For_Admin";
	}

}
