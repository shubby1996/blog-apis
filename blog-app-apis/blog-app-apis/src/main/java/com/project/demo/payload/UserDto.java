package com.project.demo.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.bytebuddy.implementation.bind.annotation.Empty;

//import org.springframework.validation.annotation.Validated;
//
//@Validated
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min=4, message = "Username must be of 4 characters")
	private String name;
	
	@Email(message = "Email not valid")
	private String email;
	
	@NotEmpty(message = "Password is necessary")
	@Size(min=3, max = 10)
	//@Pattern()---------------Regex
	private String password;
	
	@NotEmpty
	private String about;
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	
}
