package com.project.demo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.entities.User;
import com.project.demo.exceptions.ResouceNotFoundException;
import com.project.demo.payload.UserDto;
import com.project.demo.repositories.UserRepo;
import com.project.demo.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		System.out.println(userId);
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResouceNotFoundException("User", "id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser =  this.userRepo.save(user);
		
		UserDto updatedUserDto = this.userToDto(updatedUser);
		
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResouceNotFoundException("User", "Id", userId));
		UserDto gotUserDto = this.userToDto(user);
		
//		User user1 = this.userRepo.findByEmail("me.shubham@gmail.com");
//		System.out.println(user1.getName());
		
		return gotUserDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResouceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
		System.out.println("User Deleted");
		
	}

	
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setName(userDto.getName());
//		user.setId(userDto.getId());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
		
		return user;
	}
	
	
	public UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
//		userDto.setName(user.getName());
//		userDto.setId(user.getId());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
		
		return userDto;
	}
	
}
