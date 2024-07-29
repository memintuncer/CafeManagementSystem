package com.inn.cafemanagement.JWT;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;
	
	private com.inn.cafemanagement.POJO.User userDetail;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		log.info("Inside loadUserByUserName {}",email);
		userDetail = userDao.findByEmailId(email);
		if(!java.util.Objects.isNull(userDetail)) {
			return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User not found.");
		}
		
	}
	
	public com.inn.cafemanagement.POJO.User getUserDetail(){
		return userDetail;
	}

}
