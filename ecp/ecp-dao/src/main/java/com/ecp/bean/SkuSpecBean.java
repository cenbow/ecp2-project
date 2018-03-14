package com.ecp.bean;

import java.util.List;

/**
 * @ClassName SkuSpecBean
 * @Description 
 * @author Administrator
 * @Date 2017年7月7日 下午4:57:08
 * @version 1.0.0
 */
public class SkuSpecBean {
	private String spec_group;
	private List<SkuSpec> spec;
	
	public String getSpec_group() {
		return spec_group;
	}
	public void setSpec_group(String spec_group) {
		this.spec_group = spec_group;
	}
	public List<SkuSpec> getSpec() {
		return spec;
	}
	public void setSpec(List<SkuSpec> spec) {
		this.spec = spec;
	}
}
