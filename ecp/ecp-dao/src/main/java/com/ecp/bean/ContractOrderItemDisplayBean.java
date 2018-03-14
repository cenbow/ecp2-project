package com.ecp.bean;

/**
 * @ClassName ContractOrderItemDisplayBean
 * @Description 用于合同显示/预览 合同条目对象
 * @author Administrator
 * @Date 2017年9月9日 下午3:22:56
 * @version 1.0.0
 */
public class ContractOrderItemDisplayBean {
	private ContractOrderItemBean contractItem;  //合同条目对象(来自于订单)
	private String brandName; //品牌名称
	private String model; //型号
	private String parms; //技术参数;
		
	public ContractOrderItemBean getContractItem() {
		return contractItem;
	}
	public void setContractItem(ContractOrderItemBean contractItem) {
		this.contractItem = contractItem;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getParms() {
		return parms;
	}
	public void setParms(String parms) {
		this.parms = parms;
	}
	
	
}
