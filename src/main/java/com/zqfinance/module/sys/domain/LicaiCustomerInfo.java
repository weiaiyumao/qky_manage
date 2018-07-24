package com.zqfinance.module.sys.domain;


public class LicaiCustomerInfo  {
	/**
	 * 
	 */

	private Long customerid;
	private String idcard;
	private String realname;
	private String mobile;
	private String email;

	public Long getCustomerid() {
		return customerid;
	}
	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}