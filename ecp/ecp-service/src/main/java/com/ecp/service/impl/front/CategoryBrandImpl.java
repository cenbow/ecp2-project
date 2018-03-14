package com.ecp.service.impl.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

import com.ecp.bean.CategoryBrandBean;
import com.ecp.dao.CategoryBrandMapper;
import com.ecp.entity.CategoryBrand;
import com.ecp.service.front.ICategoryBrandService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class CategoryBrandImpl extends AbstractBaseService<CategoryBrand, Long> implements ICategoryBrandService {

	CategoryBrandMapper categoryBrandMapper;

	/**
	 * @param mapper
	 *            the mapper to set set方式注入
	 */
	public void setCategoryBrandMapper(CategoryBrandMapper categoryBrandMapper) {
		this.categoryBrandMapper = categoryBrandMapper;
		this.setMapper(categoryBrandMapper);
	}

	@Override
	public List<Map<String, String>> getBrandByCid(Long cid) {
		return categoryBrandMapper.getBrandByCid(cid);
	}

	@Override
	public List<CategoryBrandBean> getCategoryByBrand(String brands) {
		// 读取关键字 分词
		List<Word> words = WordSegmenter.seg(brands);
		/*
		 * for(Word word:words){ System.out.println("分词结果是："+word.toString()); }
		 */
		List<String> keywordList = new ArrayList<String>();
		for (Word word : words) {
			keywordList.add(word.toString());
		}

		return categoryBrandMapper.getCategoryByBrand(keywordList);
		
	}

	@Override
	public List<CategoryBrandBean> getCategoryByBrandIds(List<Long> ids) {
		
		return categoryBrandMapper.getCategoryByBrandIds(ids);
	}

	@Override
	public List<CategoryBrandBean> getBrandByLevelSecondCid(Long cid) {
		return categoryBrandMapper.getBrandByLevelSecondCid(cid);
	}
	
	

}
