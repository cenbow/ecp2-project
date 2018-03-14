package com.ecp.service.back;

import com.ecp.entity.CategoryBrand;
import com.ecp.service.IBaseService;

public interface ICategoryBrandService extends IBaseService<CategoryBrand, Long> {
	
	/**
	 * 保存类目品牌
	 * @param categoryBrandList
	 * @return
	 */
	public int saveCategoryBrand(Long secondLevCid, Long thirdLevCid, String brandListJson);
	
	/**
	 * 根据三级类目ID删除
	 * @param thirdLevCid
	 * @return
	 */
	public int deleteByThirdLevCid(Long thirdLevCid);
	
}
