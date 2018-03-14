package com.ecp.bean;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchCondBean
 * @Description 查询条件Bean
 * @author Administrator
 * @Date 2017年5月22日 下午6:23:56
 * @version 1.0.0
 */
public class SearchCondBean {
	private String cid;  //类目id
	private String brands; //品牌id字符串  各id以逗号分隔
	private String attrs;  //attr_id:xxx   attr_vals:xxx  各属性值对以逗号分隔
	private int pageNum;   //当前页号
	private int pageSize;  //页大小
	private String searchCond; //查询条件  for bread crumb
	
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public String getAttrs() {
		return attrs;
	}
	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearchCond() {
		return searchCond;
	}
	public void setSearchCond(String searchCond) {
		this.searchCond = searchCond;
	}
	
		
}
