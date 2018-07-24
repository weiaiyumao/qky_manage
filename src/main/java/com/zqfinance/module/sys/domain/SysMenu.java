

package com.zqfinance.module.sys.domain;

import com.zqfinance.system.common.Page;

public class SysMenu extends Page {
	/**
	 * 菜单ID
	 */
	private long id;
	/**
	 * 菜单编码
	 */
	private String menuCode;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单级别
	 */
	private String menuLevel;
	/**
	 * 菜单类型
	 */
	private String menuType;
	/**
	 * 菜单状态
	 */
	private String menuStatus;
	/**
	 * 父级菜单
	 */
	private long parentId;
	/**
	 * 菜单排序
	 */
	private String menuSort;
	/**
	 * 菜单链接
	 */
	private String menuUrl;
	/**
	 * 菜单上的按钮（非数据库字段）
	 */
	private String menuButtons;
	/**
	 * 父级菜单的名称
	 */
	private String parentName;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getMenuStatus() {
		return menuStatus;
	}
	public void setMenuStatus(String menuStatus) {
		this.menuStatus = menuStatus;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getMenuSort() {
		return menuSort;
	}
	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuButtons() {
		return menuButtons;
	}
	public void setMenuButtons(String menuButtons) {
		this.menuButtons = menuButtons;
	}
	
	public static SysMenu getFirstLevelSysMenu(SysMenu sysMenu){
		SysMenu menu = new SysMenu();
		menu.setId(sysMenu.getId());
		menu.setMenuCode(sysMenu.getMenuCode());
		menu.setMenuName(sysMenu.getMenuName());
		menu.setMenuLevel(sysMenu.getMenuLevel());
		menu.setMenuSort(sysMenu.getMenuSort());
		return menu;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
	
}

