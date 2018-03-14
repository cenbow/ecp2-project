package com.ecp.bean;

/**
 * @ClassName SkuSpec
 * @Description 用于描述sku技术参数
 * @author Administrator
 * @Date 2017年7月7日 下午4:48:52
 * @version 1.0.0
 */
public class SkuSpec {
	private String spec_key;
	private String spec_val;
	
	public String getSpec_key() {
		return spec_key;
	}
	public void setSpec_key(String spec_key) {
		this.spec_key = spec_key;
	}
	public String getSpec_val() {
		return spec_val;
	}
	public void setSpec_val(String spec_val) {
		this.spec_val = spec_val;
	}
}
