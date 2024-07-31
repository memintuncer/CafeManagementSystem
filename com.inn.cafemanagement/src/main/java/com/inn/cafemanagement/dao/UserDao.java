package com.inn.cafemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.inn.cafemanagement.POJO.User;
import com.inn.cafemanagement.wrapper.UserWrapper;

public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmailId(@Param("email") String email);
	List<UserWrapper> getAllUsers();
}