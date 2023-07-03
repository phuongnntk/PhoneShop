package com.shop.dao;

import com.shop.entity.ChiTietDonHang;
import com.shop.entity.ChiTietDonHangPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDAO extends JpaRepository<ChiTietDonHang, ChiTietDonHangPK> {
	
}
