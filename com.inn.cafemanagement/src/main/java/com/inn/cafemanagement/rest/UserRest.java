package com.inn.cafemanagement.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafemanagement.wrapper.UserWrapper;

@RequestMapping(path = "/user")
public interface UserRest {

	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required = true)Map<String, String> requestMap);
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody(required = true)Map<String, String> requestMap);
	
	@GetMapping(path = "/getall")
	public ResponseEntity<List<UserWrapper>> getAllUsers();
	
	@PostMapping(path = "/update")
	public ResponseEntity<String> updateUser(@RequestBody(required = true)Map<String, String> requestMap);
	
	@GetMapping(path = "/checkToken")
	public ResponseEntity<String> checkToken();
	
	@PostMapping(path = "/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);
	
}