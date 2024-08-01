package com.inn.cafemanagement.serviceImpl;

import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafemanagement.JWT.JWTAuthorizationFilter;
import com.inn.cafemanagement.POJO.Bill;
import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.dao.BillDao;
import com.inn.cafemanagement.document.BillPdfGenerator;
import com.inn.cafemanagement.service.BillService;
import com.inn.cafemanagement.utils.CafeManagementUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillServiceImpl implements BillService{

	@Autowired
	JWTAuthorizationFilter authorizationFilter;
	@Autowired
	BillDao billDao;
	
	@Autowired
	BillPdfGenerator billPdfGenerator;
	
	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			String fileName;
			if(validateRequestMap(requestMap)) {
				if(requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
					fileName=(String) requestMap.get("uuid");
				}
				else {
					fileName = CafeManagementUtils.getUUID();
					requestMap.put("uuid", fileName);
					insertBill(requestMap);
				}
				billPdfGenerator.generatePdf(requestMap, fileName);
				return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
			}
			return CafeManagementUtils.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeManagementUtils.getResponseEntity(CafeManagementConstants.SOMETHING_WENT_WRONG, 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void insertBill(Map<String, Object> requestMap) {
		try {
			Bill bill = generateBillFromRequestMap(requestMap);
			billDao.save(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Bill generateBillFromRequestMap(Map<String, Object> requestMap) {
		Bill bill = new Bill();
		bill.setUuid((String) requestMap.get("uuid"));
		bill.setName((String) requestMap.get("name"));
		bill.setEmail((String) requestMap.get("email"));
		bill.setContactNumber((String) requestMap.get("contactNumber"));
		bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
		bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
		bill.setProductDetails((String) requestMap.get("productDetails"));
		bill.setCreatedBy((String) requestMap.get("createdBy"));
		return bill;
	}
	
	private boolean validateRequestMap(Map<String, Object> requestMap) {
		return requestMap.containsKey("name")&&
			   requestMap.containsKey("contactNumber")&&
			   requestMap.containsKey("email")&&
			   requestMap.containsKey("paymentMethod")&&
			   requestMap.containsKey("productDetails")&&
			   requestMap.containsKey("totalAmount");
	} 

}