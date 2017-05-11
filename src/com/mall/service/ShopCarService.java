package com.mall.service;

import java.util.List;

import com.mall.domain.ShopCar;

public interface ShopCarService {

	boolean addShopCar(ShopCar sc);

	List<ShopCar> getShopCarByUser(String uid);

	void deleteByIdArray(String[] shopCarIdArray);

	ShopCar updateNumber(String sid, int number);

}
