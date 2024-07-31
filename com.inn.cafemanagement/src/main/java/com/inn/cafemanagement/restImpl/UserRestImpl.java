package com.inn.cafemanagement.restImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.service.UserService;
import com.inn.cafemanagement.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserRestImpl.class);

    @Autowired
    private UserService userService;

    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap) {
        log.info("Received signUp request");
        try {
            ResponseEntity<String> response = userService.signUp(requestMap);
            return response;
        } catch (Exception e) {
            logger.error("SignUp error: {}", e.getMessage());
            return new ResponseEntity<>(CafeManagementConstants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        log.info("Received login request");
        try {
            ResponseEntity<String> response = userService.login(requestMap);
            return response;
        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage());
            return new ResponseEntity<>(CafeManagementConstants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        log.info("Received request to get all users");
        try {
            ResponseEntity<List<UserWrapper>> response = userService.getAllUsers();
            return response;
        } catch (Exception e) {
            logger.error("Get all users error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
