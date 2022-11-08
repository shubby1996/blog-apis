package com.project.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.payload.ApiResponse;
import com.project.demo.payload.UserDto;
import com.project.demo.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST - create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
	}
	
	//PUT - update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable("userId") Integer uid){
		System.out.println("Update Started");
		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
		
		return new ResponseEntity<UserDto>(updatedUserDto,HttpStatus.OK);
	}
	
	
	//DELETE - delete user
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		
		this.userService.deleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User is deleted", true), HttpStatus.OK);
	}
	
	//GET - get user
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer uid){
		
		UserDto userDto =  this.userService.getUserById(uid);
		
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	//GetAll
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> userDtos = this.userService.getAllUsers();
		
//		return ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	
}
