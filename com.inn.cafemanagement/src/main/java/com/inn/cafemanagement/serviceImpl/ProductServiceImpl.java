package com.inn.cafemanagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.JWT.JWTAuthorizationFilter;
import com.inn.cafemanagement.POJO.Category;
import com.inn.cafemanagement.POJO.Product;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.ProductDao;
import com.inn.cafemanagement.service.ProductService;
import com.inn.cafemanagement.utils.CafeManagementUtils;
import com.inn.cafemanagement.wrapper.ProductWrapper;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	JWTAuthorizationFilter jwtAuthorizationFilter;
	@Autowired
	ProductDao productDao;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if(jwtAuthorizationFilter.isAdmin()) {
				if(validateProductMap(requestMap,false)) {
					productDao.save(getProductFromRequestMap(requestMap,false));
					return CafeManagementUtils.getResponseEntity("Product added successfully", 
							HttpStatus.OK);
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

	private Product getProductFromRequestMap(Map<String, String> requestMap, boolean isAdd) {
		Product product = new Product();
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		if(isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		}
		else {
			product.setStatus("true");
		}
		
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}
			else if(!validateId){
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProducts() {
		try {
			return new ResponseEntity<>(productDao.getAllProducts(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
