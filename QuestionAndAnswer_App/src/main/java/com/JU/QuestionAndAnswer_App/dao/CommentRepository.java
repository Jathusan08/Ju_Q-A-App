package com.JU.QuestionAndAnswer_App.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JU.QuestionAndAnswer_App.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
