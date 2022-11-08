package com.project.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	
	
}
