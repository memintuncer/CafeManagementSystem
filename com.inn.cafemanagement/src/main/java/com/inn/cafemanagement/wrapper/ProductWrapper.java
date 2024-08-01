package com.inn.cafemanagement.wrapper;

import javax.persistence.NamedQuery;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {

	private Integer id;
	private String name;
	private String description;
	private Integer price;
	private String status;
	private Integer categoryId;
	private String categoryName;
	

	public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId,
			String categoryName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	public ProductWrapper(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
//	public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId,
//			String categoryName) {
//		
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.price = price;
//		this.status = status;
//		this.categoryId = categoryId;
//		this.categoryName = categoryName;
//	}
	
}
