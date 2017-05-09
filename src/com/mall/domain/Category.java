package com.mall.domain;

import java.util.List;

public class Category {
	private String cid;
	private String name;
	private String desc;
	
	private String fid;
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	private List<Category> categorys;
	
	
	

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
	@Override
	public String toString() {
		return "Category [cid=" + cid + ", name=" + name + ", desc=" + desc
				+ ", fid=" + fid + ", categorys=" + categorys + "]";
	}
	
	
	
	
}
