package com.ecp.service.impl.back;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.ecp.dao.CategoryBrandMapper;
import com.ecp.entity.CategoryBrand;
import com.ecp.service.back.IBrandService;
import com.ecp.service.back.ICategoryBrandService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("categoryBrandServiceBean")
public class CategoryBrandServiceImpl extends AbstractBaseService<CategoryBrand, Long> implements ICategoryBrandService {

	private final Logger log = Logger.getLogger(getClass());
	
	private CategoryBrandMapper categoryBrandMapper;
	
	@Resource(name="brandServiceBean")
	private IBrandService iBrandService;//品牌

	/**
	 * @param categoryBrandMapper the categoryBrandMapper to set
	 * set方式注入
	 */
	public void setCategoryBrandMapper(CategoryBrandMapper categoryBrandMapper) {
		this.categoryBrandMapper = categoryBrandMapper;
		this.setMapper(categoryBrandMapper);
	}

	/**
	 * @see com.ecp.service.back.ICategoryBrandService#saveCategoryBrand(java.util.List)
	 * 保存类目品牌
	 */
	@Override
	@Transactional
	public int saveCategoryBrand(Long secondLevCid, Long thirdLevCid, String brandListJson) {
		log.info("类目品牌集合JSON字符串："+brandListJson);
		try {
			
			List<Map<String, Object>> currBrandMapList = iBrandService.getBrandByCategoryId(thirdLevCid);//查询原来的该类目下的品牌
			
			List<CategoryBrand> categoryBrandList = JSONArray.parseArray(brandListJson, CategoryBrand.class);
			log.info("类目品牌集合："+categoryBrandList);
			
			//删除此次保存中用户删除的类目品牌
			Iterator<Map<String, Object>> ite = currBrandMapList.iterator();
			/*while (ite.hasNext()) {
				Map<String, Object> map = (Map<String, Object>) ite.next();
				Long currCategoryBrandId = Long.valueOf(map.get("category_brand_id").toString());
				for(CategoryBrand cb : categoryBrandList){
					Long id = cb.getCategoryBrandId();
					if(id==null || id<=0l){
						continue;
					}
					if(id.equals(currCategoryBrandId)){
						break;
					}else{
						int rows = categoryBrandMapper.deleteByPrimaryKey(currCategoryBrandId);
						if(rows<=0){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return 0;
						}
					}
				}
			}*/
			while (ite.hasNext()) {
				Map<String, Object> map = (Map<String, Object>) ite.next();
				Long currCategoryBrandId = Long.valueOf(map.get("category_brand_id").toString());
				Long currbrandId = Long.valueOf(map.get("brand_id").toString());
				boolean isdel = true;
				for(CategoryBrand cb : categoryBrandList){
					Long id = cb.getCategoryBrandId();
					Long brandId = cb.getBrandId();
					if(brandId!=null && brandId.equals(currbrandId)){
						if(id==null || id<=0){
							cb.setCategoryBrandId(currCategoryBrandId);	
						}
						isdel = false;
						break;
					}
				}
				if(isdel){
					int rows = categoryBrandMapper.deleteByPrimaryKey(currCategoryBrandId);
					if(rows<=0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						break;
					}
				}
			}
			
			int rows = 0;
			//添加或修改类目品牌
			for(CategoryBrand cb : categoryBrandList){
				Long id = cb.getCategoryBrandId();
				
				if(id==null || id<=0l){
					cb.setSecondLevCid(secondLevCid);
					cb.setThirdLevCid(thirdLevCid);
					cb.setCreated(new Date());
					cb.setModified(new Date());
					rows = categoryBrandMapper.insertSelective(cb);//添加新的类目品牌
				}else{
					cb.setModified(new Date());
					rows = categoryBrandMapper.updateByPrimaryKeySelective(cb);//修改类目品牌
				}
				if(rows<=0){
					log.error("保存类目品牌异常，异常实体类"+cb);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					break;
				}
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存类目品牌异常", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;
	}

	/**
	 * @see com.ecp.service.back.ICategoryBrandService#deleteByThirdLevCid(java.lang.Long)
	 * 根据三级类目ID删除
	 */
	@Override
	public int deleteByThirdLevCid(Long thirdLevCid) {
		Example example = new Example(CategoryBrand.class);
		example.createCriteria().andEqualTo("thirdLevCid", thirdLevCid);
		return categoryBrandMapper.deleteByExample(example);
	}

}
