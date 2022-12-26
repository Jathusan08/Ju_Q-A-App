package com.JU.QuestionAndAnswer_App.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JU.QuestionAndAnswer_App.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>  {
	
	
	Optional<Post> findByUrl(String url);

}
