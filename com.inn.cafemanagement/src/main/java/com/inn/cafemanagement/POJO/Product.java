package com.inn.cafemanagement.POJO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "products")
public class Product implements Serializable{
	
	private static final long serialVersionUID = -7424783425765827332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk",nullable = false)
	private Category category;
	
	@Column(name = "deccription")
	private String deccription;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "status")
	private String status;
	
}
