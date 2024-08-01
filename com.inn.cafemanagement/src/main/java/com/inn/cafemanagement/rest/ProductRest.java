package com.inn.cafemanagement.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafemanagement.wrapper.ProductWrapper;

@RequestMapping(path = "/product")
public interface ProductRest {

	@PostMapping(path = "/add")
	ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/getAll")
	ResponseEntity<List<ProductWrapper>> getAllProducts();
}
