package com.inn.cafemanagement.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafemanagement.POJO.Bill;

@RequestMapping(path = "/bill")
public interface BillRest {

	@PostMapping(path = "/generateReport")
	ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);
	
	@GetMapping(path = "/getAll")
	ResponseEntity<List<Bill>> getBills();
	
	@GetMapping(path = "/getPdf")
	ResponseEntity<byte[]> getpdf(@RequestBody Map<String, Object> requestMap);
}
