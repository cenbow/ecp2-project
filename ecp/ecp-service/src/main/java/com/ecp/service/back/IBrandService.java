package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.Brand;
import com.ecp.service.IBaseService;

public interface IBrandService extends IBaseService<Brand, Long> {
	
	/**
	 * 查询所有未删除的品牌
	 * @return
	 */
	public List<Brand> getAll();
	
	/**
	 * 删除品牌（逻辑删除）
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 根据map中的条件查询品牌
	 * @param map
	 * @return
	 * 		返回品牌mapList
	 */
	public List<Map<String, Object>> getBrandByCategoryId(Long categoryId);
}
