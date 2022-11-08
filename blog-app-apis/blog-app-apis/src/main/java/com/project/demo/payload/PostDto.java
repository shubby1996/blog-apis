package com.project.demo.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.project.demo.entities.Category;
import com.project.demo.entities.Comment;
import com.project.demo.entities.User;

public class PostDto {
	
	private Integer postId;

	private String title;
	
	private String content;
	
	private String imageName;//="default.png";
	
	private Date addedDate;
	
	private Category category;
	
	private User user;
	
	private Set<CommentDto> comments = new HashSet<CommentDto>();
	

	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

	
	
	
}
