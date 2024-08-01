package com.inn.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafemanagement.POJO.Product;
import com.inn.cafemanagement.wrapper.ProductWrapper;

public interface ProductDao extends JpaRepository<Product, Integer> {

	
	List<ProductWrapper> getAllProducts();

}
