package com.JU.QuestionAndAnswer_App.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.RoleRepository;
import com.JU.QuestionAndAnswer_App.dao.UserRepository;
import com.JU.QuestionAndAnswer_App.dto.SignUpDto;
import com.JU.QuestionAndAnswer_App.entity.Role;
import com.JU.QuestionAndAnswer_App.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	
private UserRepository userRepository;
private RoleRepository roleRepository;
private PasswordEncoder passwordEncoder;
	
	
	public UserServiceImpl() {}
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) { // injecting the dependency
		
		this.userRepository =  userRepository;
		this.roleRepository =  roleRepository;
		this.passwordEncoder = passwordEncoder;
		
	}

	@Override
	public void saveUser(SignUpDto signUpDto) {
	
		User user = new User();
		user.setName(signUpDto.getFirstName() + " " + signUpDto.getLastName());
		user.setEmail(signUpDto.getEmail());
		
		// use Spring security to encrypt the password
		user.setPassword(this.passwordEncoder.encode(signUpDto.getPassword()));
		
		//
		Role role =  this.roleRepository.findByName("ROLE_GUEST");
		
		user.setRoles(Arrays.asList(role));
		
		// save the user data to the database
		this.userRepository.save(user);

	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return this.userRepository.findByEmail(email);
	}

}
