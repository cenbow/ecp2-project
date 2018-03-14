package com.ecp.bean;

public class CategoryBrandBean {
	
	private long brandId;
	private long thirdLevCid;
	private long secondLevCid;
	private String brandLogoUrl;
	private String brandName;
	
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public long getThirdLevCid() {
		return thirdLevCid;
	}
	public void setThirdLevCid(long thirdLevCid) {
		this.thirdLevCid = thirdLevCid;
	}
	public long getSecondLevCid() {
		return secondLevCid;
	}
	public void setSecondLevCid(long secondLevCid) {
		this.secondLevCid = secondLevCid;
	}
	public String getBrandLogoUrl() {
		return brandLogoUrl;
	}
	public void setBrandLogoUrl(String brandLogoUrl) {
		this.brandLogoUrl = brandLogoUrl;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
	
}
