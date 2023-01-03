package com.JU.QuestionAndAnswer_App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.JU.QuestionAndAnswer_App.dto.SignUpDto;
import com.JU.QuestionAndAnswer_App.entity.User;
import com.JU.QuestionAndAnswer_App.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	private UserService userService;
	
	public AuthenticationController() {}
	
	@Autowired
	public AuthenticationController(UserService userService) {
		
		this.userService = userService;
		
	}
	
	// direct to signUp view page
	@GetMapping("/signUp")
	public String showSignUpForm(Model model) {
		
		// creating an on empty object that will hold user data when they enter their details
		SignUpDto user = new SignUpDto();
		
		//now let add empty object to model
		model.addAttribute("user", user); 
		
		return "SignUp";
		
	}
	
	// handle request of user SignUp Details
	@PostMapping("/signUp/save")
	public String signUp(@Valid @ModelAttribute("user")SignUpDto signUpDto, 
			BindingResult result, 
			Model model) {
		
		// check if the user already signedUp
		User existingUser = this.userService.findByEmail(signUpDto.getEmail());
		
		if((existingUser !=null) && (existingUser.getEmail() != null) && (!existingUser.getEmail().isEmpty()) ) {
			
			// if the email already exist
			result.rejectValue("email", null,"This email already has been been registered, please either login or register with different email address");
			
		}
		
		if(result.hasErrors()) {
			
	
			//i'm passing the same model object of the user input when there is error
			model.addAttribute("user", signUpDto);
			
			return "SignUp";
		}
		
		
		this.userService.saveUser(signUpDto);
		
		return "redirect:/signUp?success";
	}
}
