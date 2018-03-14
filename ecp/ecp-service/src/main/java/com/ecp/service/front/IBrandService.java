package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.Brand;
import com.ecp.service.IBaseService;

public interface IBrandService extends IBaseService<Brand, Long> {	
	
	
	/**
	 * @Description 按 关键字 查询品牌
	 * @param keywords 查询关键字（未分词）
	 * @return
	 */
	public List<Brand> getBrandByKeywords(String keywords);
	
}
