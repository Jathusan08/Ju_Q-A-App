package com.JU.QuestionAndAnswer_App.service;

import com.JU.QuestionAndAnswer_App.dto.SignUpDto; 
import com.JU.QuestionAndAnswer_App.entity.User;

public interface UserService {

	void saveUser(SignUpDto signUpDto);

	User findByEmail(String email);
}
