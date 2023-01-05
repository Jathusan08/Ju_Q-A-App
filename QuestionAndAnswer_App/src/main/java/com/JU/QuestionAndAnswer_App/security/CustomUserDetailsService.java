package com.JU.QuestionAndAnswer_App.security;

import java.util.stream.Collectors;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.UserRepository;
import com.JU.QuestionAndAnswer_App.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CustomUserDetailsService() {}
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = this.userRepository.findByEmail(email);
		
		if(user != null) {
			
			
			// if the user is found, then we will create an instance of Spring security
			 org.springframework.security.core.userdetails.User authenticatedUser =
	                    new org.springframework.security.core.userdetails.User(
	                      user.getEmail(),
	                      user.getPassword(),
	                      user.getRoles().stream()
	                              .map((role) -> new SimpleGrantedAuthority(role.getName()))
	                              .collect(Collectors.toList())
	                    );
	            return authenticatedUser;
		}
		
		else {
			
			//if the user is not found then throw exception
			throw new UsernameNotFoundException("invalid username and password");
		}
		
		
	}

}
