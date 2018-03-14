package com.ecp.service.impl.back;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.bean.DeletedType;
import com.ecp.dao.MenuMapper;
import com.ecp.entity.Category;
import com.ecp.entity.Menu;
import com.ecp.entity.RolePermission;
import com.ecp.service.back.IMenuService;
import com.ecp.service.back.IRolePermissionService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("menuServiceBean")
public class MenuServiceImpl extends AbstractBaseService<Menu, Long> implements IMenuService {

	private MenuMapper menuMapper;

	/**
	 * @param menuMapper the menuMapper to set
	 * set方式注入
	 */
	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
		this.setMapper(menuMapper);
	}
	
	@Resource(name="rolePermissionServiceBean")
	private IRolePermissionService rolePermissionService;

	/**
	 * @see com.ecp.service.back.IMenuService#getByMenuIds(java.util.List)
	 * 根据菜单ID集合查询菜单
	 */
	@Override
	public List<Menu> getByMenuIds(List<Long> menuIds) {
		return menuMapper.getByMenuIds(menuIds);
	}

	/**
	 * @see com.ecp.service.back.IMenuService#getListAllByAsc()
	 * 根据sort_num正序查询所有菜单
	 */
	@Override
	public List<Menu> getListAllByAsc() {
		Example example = new Example(Menu.class);
		example.createCriteria().andEqualTo("deleted", DeletedType.NO);
		example.setOrderByClause("sort_num ASC");
		return menuMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public int deleteById(Long menuId) {
		int rows = deleteNodes(menuId);
		if(rows>0){
			Menu menu = new Menu();
			menu.setMenuId(menuId);
			menu.setDeleted(DeletedType.YES);
			rows = menuMapper.updateByPrimaryKeySelective(menu);
		}
		return rows;
	}
	
	/**
	 * 循环删除
	 * @param id
	 * @return
	 */
	@Transactional
	private int deleteNodes(Long pid){
		//LmCategory category = lmCategoryMapper.selectByPrimaryKey(id);
		Example example = new Example(Category.class);
		example.createCriteria().andEqualTo("parentId", pid);
		List<Menu> menuList = menuMapper.selectByExample(example);
		int rows = 1;
		for(Menu menu : menuList){
			rows = deleteNodes(menu.getMenuId());
			if(rows>0){
				Menu temp = new Menu();
				temp.setMenuId(menu.getMenuId());
				temp.setDeleted(DeletedType.YES);
				rows = menuMapper.updateByPrimaryKeySelective(temp);
				if(rows>0){
					//删除 角色关系 表
					Example e = new Example(Category.class);
					e.createCriteria().andEqualTo("permissionId", menu.getMenuId());
					RolePermission perms = new RolePermission();
					perms.setDeleted(DeletedType.YES);
					rolePermissionService.updateByExampleSelective(perms, e);
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return 0;
				}
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return 0;
			}
		}
		
		return rows;
	}

}
