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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@NamedQuery(name = "Product.getAllProducts", 
			query = "Select new com.inn.cafemanagement.wrapper.ProductWrapper(p.id,p.name,p.description,"
					+"p.price,p.status,p.category.id,p.category.name) "
					+"from Product p")


//@NamedQuery(name = "Product.getAllProducts", query = "select new com.inn.cafemanagement.wrapper.ProductWrapper(u.id , u.name , u.description , u.price , u.category.id , u.category.name , u.status) from Product u")

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
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk",nullable = false)
	private Category category;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "status")
	private String status;
	
}
