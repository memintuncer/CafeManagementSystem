package com.inn.cafemanagement.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafemanagement.rest.DashBoardRest;
import com.inn.cafemanagement.service.DashBoardService;

@RestController
public class DashBoardRestImpl implements DashBoardRest {

	@Autowired
	DashBoardService dashBoardService;
	
	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		// TODO Auto-generated method stub
		return dashBoardService.getCount();
	}

}
