package com.inn.cafemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafemanagement.POJO.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
