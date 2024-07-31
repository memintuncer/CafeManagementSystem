package com.inn.cafemanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafemanagement.POJO.Category;

public interface CategoryService {

	ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

	ResponseEntity<List<Category>> getAllCategories(String filterValue);

	ResponseEntity<String> updateCategory(Map<String, String> requestMap);

}
