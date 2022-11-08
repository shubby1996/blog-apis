package com.project.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entities.Category;
import com.project.demo.entities.Post;
import com.project.demo.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>  {

	List<Post> findAllByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String keyword);
	
}
