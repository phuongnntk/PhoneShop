package com.shop.dao;

import com.shop.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneDAO extends JpaRepository<Phone, Integer> {
	@Query("SELECT g FROM Phone g WHERE g.tenDT like %?1%")
    List<Phone> findByName(String tenDT);
}
