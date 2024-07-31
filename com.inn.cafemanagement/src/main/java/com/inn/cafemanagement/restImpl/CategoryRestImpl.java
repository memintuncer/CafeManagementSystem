package com.inn.cafemanagement.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.rest.CategoryRest;
import com.inn.cafemanagement.service.CategoryService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

@RestController
public class CategoryRestImpl implements CategoryRest {
	
	@Autowired
	CategoryService categoryService;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			return categoryService.addNewCategory(requestMap);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, 
							HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
