package com.ecp.service.impl.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.BrandMapper;
import com.ecp.entity.Brand;
import com.ecp.service.back.IBrandService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("brandServiceBean")
public class BrandServiceImpl extends AbstractBaseService<Brand, Long> implements IBrandService {

	private BrandMapper brandMapper;

	/**
	 * @param brandMapper the brandMapper to set
	 * set方式注入
	 */
	public void setBrandMapper(BrandMapper brandMapper) {
		this.brandMapper = brandMapper;
		this.setMapper(brandMapper);
	}

	/**
	 * @see com.ecp.service.back.IBrandService#getBrandByCategoryId(java.lang.Long)
	 * 根据三级类目id查询品牌
	 * @param categoryId
	 * @return
	 * 		返回品牌mapList
	 */
	@Override
	public List<Map<String, Object>> getBrandByCategoryId(Long categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryId", categoryId);
		return brandMapper.getBrandByCategoryid(map);
	}

	/**
	 * @see com.ecp.service.back.IBrandService#getAll()
	 * 查询所有未删除的品牌
	 */
	@Override
	public List<Brand> getAll() {
		Example example = new Example(Brand.class);
		example.createCriteria().andEqualTo("deleted", DeletedType.NO);
		example.setOrderByClause("brand_id DESC");
		return brandMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.IBrandService#deleteById(java.lang.Long)
	 * 删除品牌（逻辑删除）
	 */
	@Override
	public int deleteById(Long id) {
		Brand brand = new Brand();
		brand.setBrandId(id);
		brand.setDeleted(DeletedType.YES);
		return brandMapper.updateByPrimaryKeySelective(brand);
	}

}
