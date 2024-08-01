package com.inn.cafemanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafemanagement.wrapper.ProductWrapper;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

	ResponseEntity<List<ProductWrapper>> getAllProducts();

}
