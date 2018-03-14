package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.Menu;
import com.ecp.service.IBaseService;

public interface IMenuService extends IBaseService<Menu, Long> {
	
	/**
	 * 根据菜单ID集合查询菜单
	 * @param menuIds
	 * @return
	 */
	public List<Menu> getByMenuIds(List<Long> menuIds);
	
	/**
	 * 根据sort_num正序查询所有菜单
	 * @return
	 */
	public List<Menu> getListAllByAsc();
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 */
	public int deleteById(Long menuId);
	
}
