package com.ecp.bean;

import java.util.ArrayList;

import com.ecp.entity.Category;

/**
 * @ClassName CategoryTree
 * @Description 类目树结点  用于前台类目树加载（查询-类目方式）
 * @author Administrator
 * @Date 2017年5月17日 下午1:21:07
 * @version 1.0.0
 */
public class CategoryTreeNode {
	private Category category;  //当前结点
	private ArrayList<CategoryTreeNode> subCategoryList;  //此结点的直接子结点列表	
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public ArrayList<CategoryTreeNode> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(ArrayList<CategoryTreeNode> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
	
	
	
	

}
