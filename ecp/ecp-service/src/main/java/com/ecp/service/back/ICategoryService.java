package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.CategoryTreeNode;
import com.ecp.entity.Category;
import com.ecp.service.IBaseService;

public interface ICategoryService extends IBaseService<Category, Long> {
	
	/**
	 * 根据主键删除（如果删除节点中有子节点一起删除）
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	/**
	 * 根据父ID查询类目
	 * @param parentCid
	 * @return
	 */
	public List<Category> selectByPid(Long parentCid);
	/**
	 * 根据级别查询类目
	 * @param lev
	 * @return
	 */
	public List<Category> selectByLev(int lev);
	/**
	 * @Description 读取类目树
	 * @return  一级类目结点列表
	 */
	public List<CategoryTreeNode> getCategoryTree();
	
	public List<Map<String, Object>> getAllCategory();
	
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid);
	
	/**
	 * 获取所有类目（默认为sort_number正序，多个用英文逗号隔离）
	 * @param sort
	 * 			参数说明：表字段+空格+排序（正序：ASC,倒序：DESC）
	 * @return
	 */
	public List<Category> getAll(String sort);
	
	/**
	 * 根据类目id修改类目信息及类目品牌关联表信息
	 * @param category
	 * @return
	 */
	public int updateById(Category category);
	
}
