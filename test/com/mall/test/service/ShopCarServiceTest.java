package com.mall.test.service;

import org.junit.Test;

import util.BeanFactory;

import com.mall.service.ShopCarService;

public class ShopCarServiceTest {
	ShopCarService service = (ShopCarService) BeanFactory.get("ShopCarService");
	@Test
	public void Testt(){
		System.out.println(service.getShopCarByUser("961957AFC18A4A3DB9860CF050492857") );
		
	}

}
