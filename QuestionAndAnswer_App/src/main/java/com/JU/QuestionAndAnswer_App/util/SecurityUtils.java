package com.JU.QuestionAndAnswer_App.util;

import java.util.Collection; 

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {
	
	public static User getCurrentUser() {
		
		// this holds principal object that contains the username, password and roles
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		//Convert principal object into the user object from Spring Security
			if(principal instanceof User) {
				
				// if principal object instance of User object we should cast the principal object to User objet
				return (User) principal;
			}
		return null;
	}
	
	
	// we need to know what type role is the user such as if it admin or guest user
	
	
	public static String getRole() {
		
		
		User user = getCurrentUser();
		
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		
		for(GrantedAuthority authority : authorities) {
			
			
			return authority.getAuthority();
		}
		
		return null;
		
	}

}
