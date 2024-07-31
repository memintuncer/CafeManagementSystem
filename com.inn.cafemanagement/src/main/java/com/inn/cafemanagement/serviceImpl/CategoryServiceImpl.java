package com.inn.cafemanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.cafemanagement.JWT.JWTAuthorizationFilter;
import com.inn.cafemanagement.POJO.Category;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.CategoryDao;
import com.inn.cafemanagement.service.CategoryService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDao categoryDao;
	@Autowired
	JWTAuthorizationFilter jwtAuthorizationFilter;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			if(jwtAuthorizationFilter.isAdmin()) {
				if(validateCategoryMap(requestMap,false)) {
					categoryDao.save(getCategoryFromRequestMap(requestMap, false));
					return CafeManagementUtils.getResponseEntity("New Category added successfully", 
							HttpStatus.OK);
				}
			}
			else {
				return CafeManagementUtils.getResponseEntity(CafeManagementConstants.UNAUTHORIZED_ACCESS,
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, 
						HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validated) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id")&&validated) {
				return true;
			}
			else if (!validated) {
				return true;
			}
		}
		return false;
	}
	
	private Category getCategoryFromRequestMap(Map<String, String> requestMap,Boolean isAdded) {
		Category category = new Category();
		if(isAdded) {
			category.setId(Integer.parseInt(requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		return category;
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				return new ResponseEntity<List<Category>>(categoryDao.getAllCategories(),
										HttpStatus.OK);
			}
			return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtAuthorizationFilter.isAdmin()) {
				if(validateCategoryMap(requestMap, true)) {
					Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						categoryDao.save(getCategoryFromRequestMap(requestMap, true));
						return CafeManagementUtils.getResponseEntity("Category updated successfully", HttpStatus.OK);
					}
					else {
						return CafeManagementUtils.getResponseEntity("Category id does not exists", HttpStatus.OK);
					}
				}
				return CafeManagementUtils.getResponseEntity(CafeManagementConstants.INVALID_DATA, 
						HttpStatus.BAD_REQUEST);
			}
			else {
				return CafeManagementUtils.getResponseEntity(CafeManagementConstants.UNAUTHORIZED_ACCESS,
						HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
