package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.bean.CategoryBrandBean;
import com.ecp.entity.CategoryBrand;
import com.ecp.service.IBaseService;

public interface ICategoryBrandService extends IBaseService<CategoryBrand, Long> {	
	
	
	/**
	 * @Description  获取指定三级类目下的品牌
	 * @param cid
	 * @return
	 */
	public List<Map<String,String>> getBrandByCid(Long cid);
	
	
	/**
	 * @Description  获取指定二级类目下的品牌
	 * @param cid 二级类目id
	 * @return
	 */
	public List<CategoryBrandBean> getBrandByLevelSecondCid(Long cid);

	
	
	/**
	 * 关键字查询
	 * @Description 按品牌读取类目
	 * @param brands  品牌字符串
	 * @return
	 */
	public List<CategoryBrandBean> getCategoryByBrand(String brands);
	
	
	
	/**
	 * @Description 按品牌ID号查询（可有多个）
	 * @param ids
	 * @return
	 */
	public List<CategoryBrandBean> getCategoryByBrandIds(List<Long> ids);
	
	
}
