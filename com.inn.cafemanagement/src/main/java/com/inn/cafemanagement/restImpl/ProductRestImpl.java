package com.inn.cafemanagement.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.rest.ProductRest;
import com.inn.cafemanagement.service.ProductService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

@RestController
public class ProductRestImpl implements ProductRest{

	@Autowired
	ProductService productService;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			return productService.addNewProduct(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
