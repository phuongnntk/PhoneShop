package com.shop.helper;

import com.shop.entity.DonHang;

import java.util.List;


public class OrderHelper {
	public int getTotalPage(int soSanPham,List<DonHang> list) {
		int tongSoSanPham = list.size();
		int tongSoTrang = 1;
		float tempFloat = (float) tongSoSanPham / soSanPham;
		int tempInt = (int) tempFloat;
		if (tempFloat - tempInt > 0) {
			tongSoTrang = tempInt + 1;
		} else {
			tongSoTrang = tempInt;
		}
		return tongSoTrang;
	}
}
