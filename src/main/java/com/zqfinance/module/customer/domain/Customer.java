/**
 * 存放在session中的customer的对象
 */

package com.zqfinance.module.customer.domain;

import java.util.Date;
import java.util.Map;

import com.zqfinance.system.domain.BaseDomain;
import com.zqfinance.system.util.MStringUtil;

public class Customer extends BaseDomain {
	// 主键

	private static final long serialVersionUID = 2382437320192669511L;
	private String customerId;
	// 登录密码
	private String password;

	private String avatar;

	// 用户名
	private String username;
	
	private String idcard;

	// 手机号码
	private String mobile;

	// 客户等级 0=普通客户 1=VIP客户
	private Integer level;

	// 最后更新时间
	private Date updatedatetime;

	// 用户状态'1=启用 0=停用 2=未激活（借款申请用）3=未激活（注册申请用） 4=锁定 255=删除'
	private Integer status;

	// 第一次登录修改密码0 = 第一次登录已跟换过密码 1= 第一次登录未更换过密码
	private Integer firstChangePwd;

	// 用户邮箱
	private String email;

	private String qq;

	private String adress;

	// 最后更新用户ID
	private String modifyUserid;

	// 创建时间
	private Date createDatetime;

	// 是否是机器人1=机器人 0=正常客户
	private String isrobot;

	// 是否在线
	private String isonline;

	// 最后登录时间
	private Date lastLoginDatetime;

	// 是否自动登录
	private int isAutoLogin;

	private String requestIp;

	// 客户来源;客户来源：0=pc端，1=移动端
	private int frommobile;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFirstChangePwd() {
		return firstChangePwd;
	}

	public void setFirstChangePwd(Integer firstChangePwd) {
		this.firstChangePwd = firstChangePwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getModifyUserid() {
		return modifyUserid;
	}

	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getIsrobot() {
		return isrobot;
	}

	public void setIsrobot(String isrobot) {
		this.isrobot = isrobot;
	}

	public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}

	public Date getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(Date lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}

	public int getIsAutoLogin() {
		return isAutoLogin;
	}

	public void setIsAutoLogin(int isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public int getFrommobile() {
		return frommobile;
	}

	public void setFrommobile(int frommobile) {
		this.frommobile = frommobile;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Customer(){
		
	}
	public Customer(Map<String,String> map){
		if(null!=map){
			if(MStringUtil.isNotEmpty(map.get("id"))){
				this.customerId = map.get("id");
			}
			if(MStringUtil.isNotEmpty(map.get("password"))){
				this.password = map.get("password");
			}
			if(MStringUtil.isNotEmpty(map.get("avatar"))){
				this.avatar = map.get("avatar");
			}
			if(MStringUtil.isNotEmpty(map.get("username"))){
				this.username = map.get("username");
			}
			if(MStringUtil.isNotEmpty(map.get("mobile"))){
				this.mobile = map.get("mobile");
			}
			if(MStringUtil.isNotEmpty(map.get("level"))){
				this.level = Integer.parseInt(map.get("level"));
			}
			if(MStringUtil.isNotEmpty(map.get("updatedatetime"))){
				Long modifyTime = Long.parseLong(map.get("updatedatetime"));
				Date dat = new Date(modifyTime);
				this.updatedatetime = dat;
			}
			if(MStringUtil.isNotEmpty(map.get("status"))){
				this.status = Integer.parseInt(map.get("status"));
			}
			if(MStringUtil.isNotEmpty(map.get("firstChangePwd"))){
				this.firstChangePwd = Integer.parseInt(map.get("firstChangePwd"));
			}
			if(MStringUtil.isNotEmpty(map.get("email"))){
				this.email = map.get("email");
			}
			if(MStringUtil.isNotEmpty(map.get("qq"))){
				this.qq = map.get("qq");
			}
			if(MStringUtil.isNotEmpty(map.get("adress"))){
				this.adress = map.get("adress");
			}
			if(MStringUtil.isNotEmpty(map.get("modifyUserid"))){
				this.modifyUserid = map.get("modifyUserid");
			}
			if(MStringUtil.isNotEmpty(map.get("createDatetime"))){
				Long modifyTime = Long.parseLong(map.get("createDatetime"));
				Date dat = new Date(modifyTime);
				this.createDatetime = dat;
			}
			if(MStringUtil.isNotEmpty(map.get("isrobot"))){
				this.isrobot = map.get("isrobot");
			}
			if(MStringUtil.isNotEmpty(map.get("isonline"))){
				this.isonline = map.get("isonline");
			}
			if(MStringUtil.isNotEmpty(map.get("lastLoginDatetime"))){
				Long modifyTime = Long.parseLong(map.get("lastLoginDatetime"));
				Date dat = new Date(modifyTime);
				this.lastLoginDatetime = dat;
			}
			if(MStringUtil.isNotEmpty(map.get("isAutoLogin"))){
				this.isAutoLogin = Integer.parseInt(map.get("isAutoLogin"));
			}
			if(MStringUtil.isNotEmpty(map.get("requestIp"))){
				this.requestIp = map.get("requestIp");
			}
			if(MStringUtil.isNotEmpty(map.get("frommobile"))){
				this.frommobile = Integer.parseInt(map.get("frommobile"));
			}
		}
	}
}
