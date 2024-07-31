package com.inn.cafemanagement.POJO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@NamedQuery(name = "User.findByEmailId", query = "Select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUsers", 
			query = "Select new com.inn.cafemanagement.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.email,u.status)"
					+ " from User u"
					+ " where u.role='user'")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Users")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "contactNumber")
	private String contactNumber;

	@Column(name = "email") // Used for username
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "status")
	private String status;

	@Column(name = "role")
	private String role;
	
}
