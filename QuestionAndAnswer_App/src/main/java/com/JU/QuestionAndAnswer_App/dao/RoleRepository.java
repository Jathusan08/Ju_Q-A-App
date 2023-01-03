package com.JU.QuestionAndAnswer_App.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JU.QuestionAndAnswer_App.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	
	Role findByName(String name);
}
