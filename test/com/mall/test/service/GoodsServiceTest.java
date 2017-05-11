package com.mall.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import util.BeanFactory;
import util.PageBean;

import com.mall.domain.Goods;
import com.mall.service.GoodsService;

public class GoodsServiceTest {
	GoodsService service = (GoodsService) BeanFactory.get("GoodsService");
	@Test
	public void pageTest(){
		PageBean<Goods> page = new PageBean<Goods>();
		page.setAttrUrl(" g.cid='cs1' ");
//		page = service.getPage(page);
		
		
	}
	@Test
	public void randomTest(){
		System.out.println( String.format("%tF %<tT", new Date()) );
	}
	
}
