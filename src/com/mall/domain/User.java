package com.mall.domain;

public class User {
	private String uid;
	private String username;
	private String password;
	private String emailAddress;
	private int active;//0, 
	private String activeCode;//使用一次的激活码
	
	//regist
	private String surePassword;
	private String verifyCode;
	
	//change pass
	private String newPassword;
	
	public String getSurePassword() {
		return surePassword;
	}
	public void setSurePassword(String surePassword) {
		this.surePassword = surePassword;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", emailAddress=" + emailAddress + ", active="
				+ active + ", activeCode=" + activeCode + ", surePassword="
				+ surePassword + ", verifyCode=" + verifyCode
				+ ", newPassword=" + newPassword + "]";
	}

	
	
	
}
