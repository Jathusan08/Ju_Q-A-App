package com.JU.QuestionAndAnswer_App.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.service.PostService;

@Controller
public class QAController {

	private PostService postService;
	
	public QAController() {}
	
	@Autowired
	public QAController(PostService postService) {
		
		this.postService = postService;
		
	}
	
	
	// Main Root: http://localhost:8080/
	@GetMapping("/")
	public String showAllQAPosts(Model model) {
		
		// get the list of posts and add to the model so we can use that attribute to populate the data
		model.addAttribute("QAposts", this.postService.getAllPosts());
		
	
		
		return "QA/QA_Post_For_Client";
	}
	
	// direct to view specific QA POST by POST URL
	@GetMapping("/post/{postUrl}")
	public String viewQAPost(@PathVariable("postUrl") String postUrl, Model model) {
		
		// retrieve postDto object
		PostDto postDto  = this.postService.findPostByUrl(postUrl);
		
		model.addAttribute("post", postDto); 
		
		return "QA/View_post_For_Client";
		
	}
	
	// direct to search QA Post
	// // localhost:8080/post/search?query=java
	@GetMapping("/page/search")
	public String searchQAPosts(@RequestParam(value = "query") String query, Model model) {
		
		List<PostDto> posts = this.postService.searchPosts(query);
		
		model.addAttribute("QAposts", posts);
		
		
		return "QA/QA_Post_For_Client";
	}
	
}
