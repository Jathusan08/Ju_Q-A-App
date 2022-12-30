package com.JU.QuestionAndAnswer_App.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.service.PostService;

import jakarta.validation.Valid;

@Controller
public class PostController {
	
	private PostService postService;
	
	public PostController() {}
	
	@Autowired
public PostController(PostService postService) {
		
		this.postService = postService;
		
	}
	
	
	// get list of posts for Admin Users ONLY
	@GetMapping("/admin/posts")
	public String showAllPosts(Model model) {
		
		// get the list of posts and add to the model so we can use that attribute to populate the data
		model.addAttribute("post", this.postService.getAllPosts());
		
		return "Posts_For_Admin";
	}
	
	// direct to add new post
	@GetMapping("/admin/posts/newPost")
	public String newPostForm(Model model) {
		
		// create an object empty PostDto object so that i can pass that object to my view where im able to the bind the user
		// input to the object fields
		PostDto postDto = new PostDto();
		
		model.addAttribute("post", postDto);
		
		return "Create_post_For_Admin";
	}
	
	// to handle form submit request
	@PostMapping("/admin/posts")
	public String createNewPost(@Valid @ModelAttribute("post")PostDto postDto, 
								BindingResult result, 
								Model model) {
		
		// @Valid - To allow validation for PostDto Bean
		
	
	//	System.out.println("postDto " + postDto.getShortDescription());
		// BindingResult to check errors and return to UI
			if(result.hasErrors()) {
				
				// if there is any errors then hasErrors() based on the validation annotation I set up on my PostDto class
				
				System.out.println("postDto " + postDto.getShortDescription());
				
				//i'm passing the same model object of the user input when there is error
				model.addAttribute("post", postDto);
				
				return "Create_post_For_Admin";
			}
		
		postDto.setUrl(getUrl(postDto.getTitle()));
		
	// add to the database		
		this.postService.createPost(postDto);
		
		return "redirect:/admin/posts";
	}
	
	
	
	
	// direct to edit  post
	@GetMapping("/admin/posts/{postId}/edit")
	public String showEditPostForm(@PathVariable("postId") Long postId, Model model) {
		
		// @PathVariable will get the postId value from ULR
		
		// let retrive the post if from the database
		PostDto postDto = this.postService.findPostById(postId);
		
		model.addAttribute("post", postDto);
		
		System.out.println("POSTDT " + postDto);
		
		return "Edit_post_For_Admin";
		
	}
	
	// to handle edit form submit request
	 @PostMapping("/admin/posts/{postId}")
	    public String updatePost(@PathVariable("postId") Long postId,
	                             @Valid @ModelAttribute("post") PostDto post,
	                             BindingResult result,
	                             Model model){
		 String viewPage ="";
		 
	
		 
		 if((post.getContent().isEmpty() != true) && (post.getTitle().isEmpty() != true) && (post.getShortDescription().isEmpty() != true)) {
			 
			 PostDto postDto = this.postService.findPostById(postId);
			 
			   post.setId(postId);
			   post.setDateCreated(postDto.getDateCreated());			   
			   this.postService.findPostById(postId);
			   
			   System.out.println("date created " + post.getDateCreated());
			   
		       this.postService.updatePost(post);
		       
		       
		       
		       viewPage= "redirect:/admin/posts";
			 
		 }
		 
		 
		 else if(result.hasErrors()){
	        	
		
			 post.setId(postId);
	            model.addAttribute("post", post);
	            
	            
	           //viewPage= "redirect:http://localhost:8080/admin/posts/"+postId+"/edit";
	            viewPage= "Edit_post_For_Admin";
	        }

		 
		 return viewPage;
	     
	    }
	 
		// direct to edit  post
		@GetMapping("/admin/posts/{postId}/delete")
		public String deletePostForm(@PathVariable("postId") Long postId) {
			
		// delete 
			
			this.postService.deletePost(postId);
			
			return "redirect:/admin/posts";
			
		}
		
		
		// direct to view  post
		@GetMapping("/admin/posts/{postUrl}/view")
		public String viewPostForm(@PathVariable("postUrl") String postUrl, Model model) {
			
			// retrieve postDto object
			PostDto postDto  = this.postService.findPostByUrl(postUrl);
			
			model.addAttribute("post", postDto); 
			
			return "View_post_For_Admin";
			
		}
		
		// localhost:8080/admin/posts/search?query=java
		@GetMapping("/admin/posts/search")
		public String searchPosts(@RequestParam(value = "query") String query, Model model) {
			
			
			List<PostDto> posts = this.postService.searchPosts(query);
			
			model.addAttribute("post", posts);
			
			return "Posts_For_Admin";
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
