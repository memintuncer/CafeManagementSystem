package com.inn.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafemanagement.POJO.Category;

public interface CategoryDao extends JpaRepository<Category,Integer>{
	
	 List<Category> getAllCategories();
}
