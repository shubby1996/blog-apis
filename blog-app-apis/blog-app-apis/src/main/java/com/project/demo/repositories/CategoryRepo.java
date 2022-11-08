package com.project.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
