package com.mall.dao;

import java.util.List;
import java.util.Map;

import com.mall.domain.ShopCar;


public interface ShopCarDao extends BaseDao<ShopCar> {
	public List<ShopCar> getByUserId(String uid);
	public ShopCar getByUidGid(Map<String, String> map);
	public void deleteByIdArray(String[] shopCarIdArray);
	public ShopCar getBySidGoods(String sid);
	public List<ShopCar> getBySidArray(String[] shopCarIdArray);
}
