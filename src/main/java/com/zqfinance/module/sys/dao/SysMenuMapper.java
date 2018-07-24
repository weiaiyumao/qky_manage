

package com.zqfinance.module.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zqfinance.module.sys.domain.SysButton;
import com.zqfinance.module.sys.domain.SysMenu;

public interface SysMenuMapper {
	/**
	 * 查询菜单列表
	 * @author 许进
	 */
	public List<SysMenu> getSysMenuList(SysMenu sysMenu);
	/**
	 * 获取菜单记录数
	 * @author 许进
	 */
	public int getSysMenuCount(SysMenu sysMenu);
	/**
	 * 根据菜单编码取得菜单列表
	 * @author 许进
	 */
	public List<SysMenu> getSysMenuByMenuCode(String menuCode);
	
	/**
	 * 新增菜单
	 * @author 许进
	 */
	public void saveMenu(SysMenu sysMenu);
	/**
	 * 新增按钮
	 * @author 许进
	 */
	public void saveButton(@Param("sysButtonList")List<SysButton> sysButtonList);
	/**
	 * 取得所有的一级菜单的列表
	 * @author 许进
	 */
	public List<SysMenu> getFirstMenuList();
	
	/**
	 * 根据菜单ID取得菜单的对象
	 * @author 许进
	 */
	public SysMenu getSysMenuById(long menuId);
	/**
	 * 根据菜单ID取得按钮的列表
	 * @author 许进
	 */
	public List<SysButton> getButtonListByMenuId(long menuId);
	/**
	 * 修改菜单信息
	 * @author 许进
	 */
	public void updateSysMenu(SysMenu sysMenu);
	/**
	 * 删除按钮列表
	 * @author 许进
	 */
	public void delSysButtonList(@Param("idList")List<Long> idList);
	/**
	 * 根据菜单ID删除按钮
	 * @author 许进
	 */
	public void delSysButtonByMenuId(long menuId);
	/**
	 * 删除菜单
	 */
	public void delMenuById(long menuId);
}

