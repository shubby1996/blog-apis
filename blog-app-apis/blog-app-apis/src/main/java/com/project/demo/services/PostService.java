package com.project.demo.services;

import java.util.List;

import com.project.demo.entities.Post;
import com.project.demo.payload.PostDto;
import com.project.demo.payload.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//getAllPost
	List<PostDto> getAllPost();
	
	//get single post
	PostDto getPostById(Integer postId);

	//getAllPostsByCategory
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	//getAllPostsByUser
	List<PostDto> getAllPostsByUser(Integer userId);

	List<PostDto> getAllPostUsingPage(Integer pageSize, Integer pageNumber);
	
	PostResponse getAllPostUsingPagnationDtosAndApiResponse(Integer pageSize, Integer pageNumber);

	PostResponse getAllPostUsingPagnationDtosAndApiResponseAndSorting(Integer pageSize, Integer pageNumber,String sortBy,String sortDir);
	
	List<PostDto> searchPostsByTitle(String keyword);
	
	
	

}
