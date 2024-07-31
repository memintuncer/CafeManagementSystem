package com.inn.cafemanagement.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.cafemanagement.POJO.Category;

@RequestMapping(path = "/category")
public interface CategoryRest {

	@PostMapping(path = "/add")
	ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);
	@GetMapping(path = "/getall")
	ResponseEntity<List<Category>> getAllCategories(@RequestParam(required = false) String filterValue);

}
