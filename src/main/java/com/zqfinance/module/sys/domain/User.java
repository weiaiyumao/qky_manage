
package com.zqfinance.module.sys.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.zqfinance.system.common.Page;

public class User extends Page {

	private long id;

	private String cardNo;

	private String	userName;

	private String userNameCn;

	private String userNameEn;

	private String userStatus;

	private String userPass;
	
	private String userPwd;
	
	private String userPassword;

	private String lastLoginTime;

	private String lastLoginIp;
	
	private Date createTime;
	
	private String roleIds;
	
    /**
     * 权限集合
     */
    @SuppressWarnings("unused")
	private Set<GrantedAuthority> authorities;
    /**
     * 账号是否过期
     */
    @SuppressWarnings("unused")
	private boolean accountNonExpired = true;
    /**
     * 账号是否锁定
     */
    @SuppressWarnings("unused")
	private boolean accountNonLocked = true;
    /**
     * 用户凭证是否过期
     */
    @SuppressWarnings("unused")
	private boolean credentialsNonExpired = true;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameCn() {
		return userNameCn;
	}

	public void setUserNameCn(String userNameCn) {
		this.userNameCn = userNameCn;
	}

	public String getUserNameEn() {
		return userNameEn;
	}

	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}

