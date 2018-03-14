package com.ecp.service.front;

import java.util.List;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.CategoryTreeNode;
import com.ecp.entity.Category;
import com.ecp.service.IBaseService;

public interface ICategoryService extends IBaseService<Category, Long> {	
	
	/**
	 * @Description 读取类目树
	 * @return  一级类目结点列表
	 */
	public List<CategoryTreeNode> getCategoryTree();
	
	public List<Category> getAllCategory();
	
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid);
	
	
	/**
	 * @Description 通过cid获取类目
	 * @param cid
	 * @return
	 */
	public Category getCategoryByCid(long cid);
	
	
	/**
	 * @Description 获取父结点为parentCid的节点列表
	 * @param parentCid
	 * @return
	 */
	public List<Category> getCategoryByParentCid(long parentCid);
	
	/**
	 * @Description 通过关键字查询类目(叶子结点)
	 * @param keywords  关键字列表
	 * @return
	 */
	public List<Category> getCategoryByKeywords(String keywords);
}
