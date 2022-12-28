package com.JU.QuestionAndAnswer_App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.service.PostService;

import jakarta.validation.Valid;

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
	
	// direct to add new post
	@GetMapping("/admin/posts/newPost")
	public String newPostForm(Model model) {
		
		// create an object empty PostDto object so that i can pass that object to my view where im able to the bind the user
		// input to the object fields
		PostDto postDto = new PostDto();
		
		model.addAttribute("newPost", postDto);
		
		return "Create_post_For_Admin";
	}
	
	// to handle form submit request
	@PostMapping("/admin/posts")
	public String createNewPost(@Valid @ModelAttribute("newPost")PostDto postDto, 
								BindingResult result, 
								Model model) {
		
		// @Valid - To allow validation for PostDto Bean
		
		// BindingResult to check errors and return to UI
			if(result.hasErrors()) {
				
				// if there is any errors then hasErrors() based on the validation annotation I set up on my PostDto class
				
				
				//i'm passing the same model object of the user input when there is error
				model.addAttribute("newPost", postDto);
				
				return "Create_post_For_Admin";
			}
		
		postDto.setUrl(getUrl(postDto.getTitle()));
		
	// add to the database		
		this.postService.createPost(postDto);
		
		return "redirect:/admin/posts";
	}

	
	// create post URL
	private static String getUrl(String postTitle) {
		// What-Is-Difference-Between-Array-And-Arraylist
		// what-is-difference-between-array-and-arraylist
		
		String title = postTitle.trim().toLowerCase();
		String url = title.replaceAll("\\s+", "-"); // replace space with hyphen
		url= url.replaceAll("[^A-Za-z0-9]", "-");
		
		return url;
		
		
	}
}
