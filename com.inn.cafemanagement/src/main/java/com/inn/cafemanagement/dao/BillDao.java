package com.inn.cafemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafemanagement.POJO.Bill;

public interface BillDao extends JpaRepository<Bill,Integer>{

}
