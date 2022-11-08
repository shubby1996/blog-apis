package com.project.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
//		System.out.println("end");
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();  	
	}
}




/* To clean ports
 * 
 * netstat -a -o -n
 * taskkill /F /PID 10820
 * 
 * 
 * 	
 * */