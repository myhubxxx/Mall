package com.mall.domain;

public class Goods {
	private String gid;
	private String gname;
	private double price;
	private double nowPrice;
	private double discount;
	private String producetime;
	
	private String image_w;
	private String image_b;
	private Category category;
	
	private String desc_1;
	private String desc_2;
	private String desc_3;
	private String desc_4;
	private String desc_5;
	private String desc_6;
	private String desc_7;
	private String desc_8;
	

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}



	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(double nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getImage_b() {
		return image_b;
	}

	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getProducetime() {
		return producetime;
	}

	public void setProducetime(String producetime) {
		this.producetime = producetime;
	}

	public String getImage_w() {
		return image_w;
	}

	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDesc_1() {
		return desc_1;
	}

	public void setDesc_1(String desc_1) {
		this.desc_1 = desc_1;
	}

	public String getDesc_2() {
		return desc_2;
	}

	public void setDesc_2(String desc_2) {
		this.desc_2 = desc_2;
	}

	public String getDesc_3() {
		return desc_3;
	}

	public void setDesc_3(String desc_3) {
		this.desc_3 = desc_3;
	}

	public String getDesc_4() {
		return desc_4;
	}

	public void setDesc_4(String desc_4) {
		this.desc_4 = desc_4;
	}

	public String getDesc_5() {
		return desc_5;
	}

	public void setDesc_5(String desc_5) {
		this.desc_5 = desc_5;
	}

	public String getDesc_6() {
		return desc_6;
	}

	public void setDesc_6(String desc_6) {
		this.desc_6 = desc_6;
	}

	public String getDesc_7() {
		return desc_7;
	}

	public void setDesc_7(String desc_7) {
		this.desc_7 = desc_7;
	}

	public String getDesc_8() {
		return desc_8;
	}

	public void setDesc_8(String desc_8) {
		this.desc_8 = desc_8;
	}

	@Override
	public String toString() {
		return "Goods [gid=" + gid + ", gname=" + gname + ", price=" + price
				+ ", nowPrice=" + nowPrice + ", discount=" + discount
				+ ", producetime=" + producetime + ", image_w=" + image_w
				+ ", image_b=" + image_b + ", category=" + category
				+ ", desc_1=" + desc_1 + ", desc_2=" + desc_2 + ", desc_3="
				+ desc_3 + ", desc_4=" + desc_4 + ", desc_5=" + desc_5
				+ ", desc_6=" + desc_6 + ", desc_7=" + desc_7 + ", desc_8="
				+ desc_8 + "]";
	}
	
	
}
