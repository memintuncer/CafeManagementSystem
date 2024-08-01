package com.inn.cafemanagement.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.inn.cafemanagement.POJO.Product;
import com.inn.cafemanagement.wrapper.ProductWrapper;

public interface ProductDao extends JpaRepository<Product, Integer> {

	
	List<ProductWrapper> getAllProducts();
	
	@Modifying
	@Transactional
	Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

}
