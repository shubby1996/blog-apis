package com.project.demo.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.project.demo.entities.Category;
import com.project.demo.entities.User;
import com.project.demo.exceptions.ResouceNotFoundException;
import com.project.demo.payload.CategoryDto;
import com.project.demo.payload.UserDto;
import com.project.demo.repositories.CategoryRepo;
import com.project.demo.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
	
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category", "Id", categoryId ));
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updatedCategory =  this.categoryRepo.save(category);
		
		CategoryDto updatedCategoryDto = this.modelMapper.map(updatedCategory, CategoryDto.class);
		
		
		return updatedCategoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category", "Id", categoryId ));
		this.categoryRepo.deleteById(categoryId);

		System.out.println("User Deleted");
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResouceNotFoundException("Category", "Id", categoryId));
		CategoryDto gotCategoryDto = this.modelMapper.map(category, CategoryDto.class);
		return gotCategoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

}
