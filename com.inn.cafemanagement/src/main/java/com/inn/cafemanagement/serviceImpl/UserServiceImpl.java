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
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.JWT.CustomerUsersDetailsService;
import com.inn.cafemanagement.JWT.JWTAuthorizationFilter;
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
    
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;
    
    
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
    	log.info("Inside login {}", requestMap);
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (auth.isAuthenticated()) {
                if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(
                    		customerUsersDetailsService.getUserDetail().getEmail(), customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for Admin Approvel." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
    	 try {
             if (jwtAuthorizationFilter.isAdmin()) {
                 return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
             } else {
                 return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
             }

         } catch (Exception ex) {
             ex.printStackTrace();
         }
         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@Override
	public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
		try {
			if(jwtAuthorizationFilter.isAdmin()) {
				int userId = Integer.parseInt(requestMap.get("id"));
				String status = requestMap.get("status");
								java.util.Optional<User> optional = userDao.findById(userId);
				if(!optional.isEmpty()) {
					userDao.updateStatus(status,userId);
					return CafeManagementUtils.getResponseEntity("User status updated successfully", HttpStatus.OK);
				}
				else {
					return CafeManagementUtils.getResponseEntity("User id does not exists", HttpStatus.OK);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<String> checkToken() {
		return CafeManagementUtils.getResponseEntity("true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			User user = userDao.findByEmail(jwtAuthorizationFilter.getCurrentUsername());
			if(!user.equals(null)) {
				if(user.getPassword().equals(requestMap.get("oldPassword"))) {
					user.setPassword(requestMap.get("newPassword"));
					userDao.save(user);
					return CafeManagementUtils.getResponseEntity("Password updated successfully", HttpStatus.OK);
				}
				return CafeManagementUtils.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
			}
			return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}
		
	
	
}
