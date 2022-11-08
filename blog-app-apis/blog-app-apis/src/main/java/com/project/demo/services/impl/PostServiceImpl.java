package com.project.demo.services.impl;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.id.IntegralDataTypeHolder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.demo.entities.Category;
import com.project.demo.entities.Post;
import com.project.demo.entities.User;
import com.project.demo.exceptions.ResouceNotFoundException;
import com.project.demo.payload.PostDto;
import com.project.demo.payload.PostResponse;
import com.project.demo.repositories.CategoryRepo;
import com.project.demo.repositories.PostRepo;
import com.project.demo.repositories.UserRepo;
import com.project.demo.services.PostService;
import org.springframework.data.domain.*;

import net.bytebuddy.asm.Advice.This;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private	 PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		
		Post post = this.modelMapper.map(postDto,Post.class);
		 
		post.setImageName("defalt.png");
		post.setAddedDate(new Date());
		post.setUser(userRepo.findById(userId).orElseThrow(()->new ResouceNotFoundException("User", "Id", userId)));
		post.setCategory(categoryRepo.findById(categoryId).orElseThrow(()->new ResouceNotFoundException("User", "Id", categoryId)));
		
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post resPost = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","Id", postId));
		this.postRepo.delete(resPost);
	}


	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResouceNotFoundException("Post", "Id", postId));
//		System.out.println(post.getComments().stream().map((comment)->comment.getContent()).toList());
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
//		System.out.println(postDto.getComments().stream().map(comment->comment.getContent()).toList());		
		return postDto;
	}	

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		List<Post> listOfPosts =  this.postRepo.findByCategory(this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category","Id", categoryId)));
		
//		List<PostDto> resList = new ArrayList<PostDto>();
//		for(Post post:listOfPosts) {
//			resList.add(this.modelMapper.map(post, PostDto.class));
//		}
		
		List<PostDto> resList  = listOfPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return resList;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {

		List<Post> listOfPosts =  this.postRepo.findAllByUser(this.userRepo.findById(userId).orElseThrow(()-> new ResouceNotFoundException("Category","Id", userId)));
		
		List<PostDto> resList  = listOfPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return resList;
	}
	
	@Override
	public List<PostDto> getAllPost(){
		List<Post> listOfListPosts = this.postRepo.findAll();
		
		
		List<PostDto> resList = listOfListPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return resList;
		
	}
	
	@Override
	public List<PostDto> getAllPostUsingPage(Integer pageSize, Integer pageNumber){
		PageRequest p = PageRequest.of(pageNumber, pageSize);
		Page<Post> listOfListPosts = this.postRepo.findAll(p);
//		List<Post> listOfListPosts = this.postRepo.findAll();
		List<PostDto> resList = listOfListPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return resList;
	}
	
	@Override
	public PostResponse getAllPostUsingPagnationDtosAndApiResponse(Integer pageSize, Integer pageNumber) {
		
		PageRequest p = PageRequest.of(pageNumber, pageSize);
		Page<Post> listOfListPosts = this.postRepo.findAll(p);
//		List<Post> listOfListPosts = this.postRepo.findAll();
		List<PostDto> resList = listOfListPosts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse  = new PostResponse();
		
		postResponse.setContent(resList);
		
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		
		postResponse.setTotalElements(listOfListPosts.getTotalElements());
		postResponse.setTotalPages(listOfListPosts.getTotalPages());
		
		postResponse.setLastPage(listOfListPosts.isLast());
		return postResponse;	
	}
	
	@Override
	public PostResponse getAllPostUsingPagnationDtosAndApiResponseAndSorting(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {

		
//		org.springframework.data.domain.Pageable pageRequest  = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy));
		
		org.springframework.data.domain.Pageable pageRequest = PageRequest.of(pageNumber, pageSize, org.springframework.data.domain.Sort.by(sortBy).descending());
		
		Page<Post> listOfPosts = this.postRepo.findAll(pageRequest);
		
		List<Post> content = listOfPosts.getContent();
		
		List<PostDto> postDtos = content.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse  = new PostResponse();
		
		postResponse.setContent(postDtos);
		
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		
		postResponse.setTotalElements(listOfPosts.getTotalElements());
		postResponse.setTotalPages(listOfPosts.getTotalPages());
		
		postResponse.setLastPage(listOfPosts.isLast());
		
		return postResponse;	
	}	
	
	public List<PostDto> searchPostsByTitle(String keyword){
		
		List<Post> findByTitleContaining = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = findByTitleContaining.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
		
		
	}
	
}
