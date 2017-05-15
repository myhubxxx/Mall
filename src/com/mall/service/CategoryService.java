package com.mall.service;

import java.util.List;

import com.mall.domain.Category;

public interface CategoryService {
	
	public List<Category> getAll();

	public void addCategoryFirst(Category form);

	public void addCategorySecond(Category form);

	public void updateCategory(Category form);

	public void deleteByFirstId(String cid);
	public void deleteBySecondId(String cid);
		
	public Category getFirstCategoryId(String cid);
	public Category getSecondCategoryId(String cid);

	public List<Category> getCategoryByFid(String fid);
}
