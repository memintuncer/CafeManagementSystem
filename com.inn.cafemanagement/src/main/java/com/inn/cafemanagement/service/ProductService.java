package com.inn.cafemanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafemanagement.wrapper.ProductWrapper;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

	ResponseEntity<List<ProductWrapper>> getAllProducts();

	ResponseEntity<String> updateProduct(Map<String, String> requestMap);

	ResponseEntity<String> deleteProduct(Integer id);

	ResponseEntity<String> updateProductStatus(Map<String, String> requestMap);

	ResponseEntity<List<ProductWrapper>> getAllProductsByCategory(Integer categoryId);

	ResponseEntity<ProductWrapper> getProductById(Integer id);

}
