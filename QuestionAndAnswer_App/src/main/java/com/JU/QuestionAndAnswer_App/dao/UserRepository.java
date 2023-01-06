package com.JU.QuestionAndAnswer_App.dao;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.JU.QuestionAndAnswer_App.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

}
