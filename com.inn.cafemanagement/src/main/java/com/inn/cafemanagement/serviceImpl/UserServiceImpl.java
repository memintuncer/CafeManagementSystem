package com.inn.cafemanagement.serviceImpl;


import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.POJO.User;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.UserDao;
import com.inn.cafemanagement.service.UserService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside signup {}", requestMap);
		try{
			if(validateSignUpMap(requestMap)) {
				User user = userDao.findByEmailId(requestMap.get("email"));
				if(Objects.isNull(user)) {
					userDao.save(getUserFromMap(requestMap));
					return CafeManagementUtils.getResponseEntity("Successfully registered", HttpStatus.OK);
				}
				else {
					return CafeManagementUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
				}
			}
			else {
				return CafeManagementUtils.getResponseEntity(CafeManagementConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
	}
	
	private boolean validateSignUpMap(Map<String, String> requestMap) {

        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }
	
	
	private User getUserFromMap(Map<String,String> requestMap) {
		
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
	}
}