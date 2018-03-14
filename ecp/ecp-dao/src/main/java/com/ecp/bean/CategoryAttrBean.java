package com.ecp.bean;

import java.io.Serializable;

public class CategoryAttrBean implements Serializable {
	/**
	 * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Long cid;
	private Long attr_id;
	private String attr_name;
	private int attr_type;
	
	private int option_type;
	private int input_type;
	private int sort_number;
	
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(Long attr_id) {
		this.attr_id = attr_id;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOption_type() {
		return option_type;
	}
	public void setOption_type(int option_type) {
		this.option_type = option_type;
	}
	public int getInput_type() {
		return input_type;
	}
	public void setInput_type(int input_type) {
		this.input_type = input_type;
	}
	public int getSort_number() {
		return sort_number;
	}
	public void setSort_number(int sort_number) {
		this.sort_number = sort_number;
	}
	public int getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(int attr_type) {
		this.attr_type = attr_type;
	}
	
	 
	
}
