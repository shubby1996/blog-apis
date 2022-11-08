package com.project.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.demo.entities.User;
import com.project.demo.exceptions.ResouceNotFoundException;
import com.project.demo.repositories.UserRepo;

public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		
		//loading data from database as required
		//for our case, we will treat email of user entity as username and password of user entity as password
		
		 User user = this.userRepo.findByEmail(email).orElseThrow(()->new ResouceNotFoundException("User","Email "+email,0));
		
//		System.out.println(user.getEmail());
//		System.out.println(user.getPassword());
		return null;
	}

}
