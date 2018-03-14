package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.entity.CategoryAttr;

import tk.mybatis.mapper.common.Mapper;

public interface CategoryAttrMapper extends Mapper<CategoryAttr> {
	
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid);
	
	public List<CategoryAttrBean> getCategoryAttrByCidAndType(@Param("cid") Long cid, @Param("attr_type") int attr_type);	
	
	public List<Map<String,String>> getCategoryAttrListByCid1(Long cid);
	
	public List<CategoryAttr> findByCid(@Param("cid") long cid);
	
	
	public List<Map<String,String>> getCategoryAttrValList(@Param("cid") Long cid, @Param("attrId") Long attrId); 
}