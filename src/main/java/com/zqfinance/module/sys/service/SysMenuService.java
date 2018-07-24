/**
 * 菜单按钮Service
 * @author 许进
 */

package com.zqfinance.module.sys.service;

import java.util.List;

import com.zqfinance.module.sys.domain.SysButton;
import com.zqfinance.module.sys.domain.SysMenu;

public interface SysMenuService {
	/**
	 * 查询菜单列表
	 * @author 许进
	 */
	public List<SysMenu> getSysMenuList(SysMenu sysMenu);
	/**
	 * 获取菜单总记录数
	 * @author 许进
	 */
	public int getSysMenuCount(SysMenu sysMenu);
	/**
	 * 根据菜单编码取得菜单列表
	 * @author 许进
	 */
	public List<SysMenu> getMenuByMenuCode(String menuCode);
	/**
	 * 新增菜单，按钮，菜单与按钮的对应关系
	 * @author 许进
	 */
	public void saveMenu(SysMenu sysMenu);
	/**
	 * 修改菜单
	 * @author 许进
	 */
	public void updateMenu(SysMenu sysMenu);
	/**
	 * 查询出所有的一级菜单
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
	 * 删除菜单
	 * @author 许进
	 */
	public void delMenu(long menu);
}

