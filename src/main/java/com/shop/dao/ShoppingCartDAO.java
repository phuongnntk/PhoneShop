package com.shop.dao;

import com.shop.entity.ShoppingCart;

import java.util.Collection;

public interface ShoppingCartDAO {
	public void add(ShoppingCart item);
	public void remove(int id);
	public int getAmout();
	public int getCount();
	public Collection<ShoppingCart> getAll();
	public void clear();
	public ShoppingCart update(int idDT, int soLuong);
}
