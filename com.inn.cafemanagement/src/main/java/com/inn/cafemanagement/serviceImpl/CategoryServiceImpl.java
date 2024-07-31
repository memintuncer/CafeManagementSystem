package com.inn.cafemanagement.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.JWT.JWTAuthorizationFilter;
import com.inn.cafemanagement.POJO.Category;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.CategoryDao;
import com.inn.cafemanagement.service.CategoryService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

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
}
