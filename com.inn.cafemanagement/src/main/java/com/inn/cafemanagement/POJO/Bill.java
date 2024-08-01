package com .inn.cafemanagement.POJO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@NamedQuery(name = "Bill.getAllBills",
			query = "Select b "
					+ "from bill b "
					+ "Order By b.id Desc")

@NamedQuery(name = "Bill.getBillByUserName",
			query = "Select b "
					+ "From bill b "
					+ "Where b.createdBy=:userName "
					+ "Order By b.id Desc")


@Data
@DynamicInsert
@DynamicUpdate
@Entity(name = "bill")
public class Bill implements Serializable {
	
	private static final long serialVersionUID = -5973023212111078567L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	
	@Column(name = "contactnumber")
	private String contactNumber;
	
	@Column(name = "paymentmethod")
	private String paymentMethod;
	
	@Column(name = "total")
	private Integer total;
	
	@Column(name = "productdetails", columnDefinition = "json")
	private String productDetails;
	
	@Column(name = "createdby")
	private String createdBy;
	
}
