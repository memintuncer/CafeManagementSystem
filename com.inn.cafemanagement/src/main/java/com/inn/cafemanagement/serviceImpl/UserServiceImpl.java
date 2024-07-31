package com.inn.cafemanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.JWT.CustomerUsersDetailsService;
import com.inn.cafemanagement.JWT.JwtUtil;
import com.inn.cafemanagement.POJO.User;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.UserDao;
import com.inn.cafemanagement.service.UserService;
import com.inn.cafemanagement.utils.CafeManagementUtils;
import com.inn.cafemanagement.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeManagementUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
                } else {
                    return CafeManagementUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeManagementUtils.getResponseEntity(CafeManagementConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            logger.error("SignUp error: {}", ex.getMessage());
            return CafeManagementUtils.getResponseEntity(CafeManagementConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
    }
    
    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }
    
    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password")); // Consider hashing the password
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication in the context
            
            if (authentication.isAuthenticated()) {
                String email = requestMap.get("email");
                String role = customerUsersDetailsService.loadUserByUsername(email).getAuthorities().toString(); // Adjust according to actual role
                if ("true".equalsIgnoreCase(customerUsersDetailsService.getUserDetail().getStatus())) {
                    return new ResponseEntity<>(String.format("{\"token\":\"%s\"}", jwtUtil.generateToken(email, role)), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(String.format("{\"message\":\"%s\"}", "Wait for admin approval."), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage());
        }
        return new ResponseEntity<>(String.format("{\"message\":\"%s\"}", "Bad Request."), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Get all users error: {}", e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
