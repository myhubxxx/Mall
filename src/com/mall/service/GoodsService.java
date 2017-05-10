package com.mall.service;

import java.util.Map;

import util.PageBean;

import com.mall.domain.Goods;

public interface GoodsService {

	PageBean<Goods> getPage(PageBean<Goods> page, Map<String, Object> map);

	Goods getByGid(String gid);

}
