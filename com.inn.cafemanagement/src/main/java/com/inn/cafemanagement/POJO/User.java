package com.inn.cafemanagement.POJO;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery(name = "User.findByEmailId", query = "Select u from User u where u.email=:email")


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
