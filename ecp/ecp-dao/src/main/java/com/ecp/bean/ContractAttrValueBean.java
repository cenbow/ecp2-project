package com.ecp.bean;

/**
 * @ClassName ContractAttrValueBean
 * @Description 用于生成合同（合同属性列表）
 * @author Administrator
 * @Date 2017年6月29日 下午3:51:49
 * @version 1.0.0
 */
public class ContractAttrValueBean {
	/*
	 "attrName":"guarantee_period",
					"comment":"质保期",
					"id":1,
					"templateId":1,
					"attrvalue":"1"},
	 */
	
	private String attrName;
	private String comment;
	private Long id;
	private Long templateId;
	private String attrValue;
	
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	
	
}
