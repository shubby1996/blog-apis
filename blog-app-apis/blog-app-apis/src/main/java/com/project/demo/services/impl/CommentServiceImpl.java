package com.project.demo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entities.Comment;
import com.project.demo.entities.Post;
import com.project.demo.exceptions.ResouceNotFoundException;
import com.project.demo.payload.CommentDto;
import com.project.demo.repositories.CommentRepo;
import com.project.demo.repositories.PostRepo;
import com.project.demo.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepo commentRepo;

	@Autowired
	PostRepo postRepo;
	
	@Autowired
	ModelMapper modelMapper;  
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResouceNotFoundException("Post","Id",postId));
		
		Comment commentToAdd = this.modelMapper.map(commentDto, Comment.class);
		
//		System.out.println(commentToAdd.getContent());
		commentToAdd.setPost(post);
		Comment savedComment = this.commentRepo.save(commentToAdd);
		
//		System.out.println(this.modelMapper.map(savedComment, CommentDto.class).getContent());
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResouceNotFoundException("Comment", "Id", commentId));
		
		this.commentRepo.delete(comment);
	}

}
