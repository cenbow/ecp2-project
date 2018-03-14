package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.bean.CategoryBrandBean;
import com.ecp.entity.CategoryBrand;

import tk.mybatis.mapper.common.Mapper;

public interface CategoryBrandMapper extends Mapper<CategoryBrand> {
	
	/**
	 * @Description 读取三级类目下的brands
	 * @param cid
	 * @return
	 */
	public List<Map<String,String>> getBrandByCid(@Param("cid") Long cid);  
	
	/**
	 * @Description 读取二级类目下的brands
	 * @param cid 
	 * @return
	 */
	public List<CategoryBrandBean> getBrandByLevelSecondCid(@Param("cid") Long cid);
	
	/**
	 * @Description 根据品牌名称列表查询类目
	 * @param brands
	 * @return
	 */
	public List<CategoryBrandBean> getCategoryByBrand(@Param("brands") List<String> brands);
	
	
	/**
	 * @Description 通过品牌id查询 
	 * @param ids
	 * @return
	 */
	public List<CategoryBrandBean> getCategoryByBrandIds(@Param("ids") List<Long> ids);
	
}