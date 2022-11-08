package com.project.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.payload.ApiResponse;
import com.project.demo.payload.CommentDto;
import com.project.demo.services.CommentService;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
	
	@Autowired
	CommentService commentService;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDto> createComment( @RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){
		CommentDto createdComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment( @PathVariable("commentId") Integer commentId){
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleted successfully", true), HttpStatus.OK);
	}
	
	
}
