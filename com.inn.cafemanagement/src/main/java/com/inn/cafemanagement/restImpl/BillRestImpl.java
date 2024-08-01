package com.inn.cafemanagement.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.rest.BillRest;
import com.inn.cafemanagement.service.BillService;
import com.inn.cafemanagement.utils.CafeManagementUtils;

@RestController
public class BillRestImpl implements BillRest{
	
	@Autowired
	BillService billService;
	
	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			return billService.generateReport(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, 
									HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
