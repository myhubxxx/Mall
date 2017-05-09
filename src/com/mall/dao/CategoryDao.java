package com.mall.dao;

import java.util.List;

import com.mall.domain.Category;

public interface CategoryDao extends BaseDao<Category> {
	public List<Category> getAllCategory();
	public void addFirst  (Category category);
	public void addSecond (Category category);
	public Category getByFirstId  (String cid);
	public Category getBySecondId (String cid);
	public Category getBySecondFid (String fid);
	public void updateByFirstId  (Category category);
	public void updateBySecondId (Category category);
	public void deleteByFirstId  (String cid);
	public void deleteBySecondId (String cid);
	public void deleteBySecondFid(String fid);
}
