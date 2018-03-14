package com.ecp.service.impl.front;

import java.util.ArrayList;
import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

import com.ecp.dao.BrandMapper;
import com.ecp.entity.Brand;
import com.ecp.service.front.IBrandService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class BrandServiceImpl extends AbstractBaseService<Brand, Long> implements IBrandService {

	BrandMapper brandMapper;

	/**
	 * @param mapper
	 *            the mapper to set set方式注入
	 */
	public void setBrandMapper(BrandMapper brandMapper) {
		this.brandMapper = brandMapper;
		this.setMapper(brandMapper);
	}

	@Override
	public List<Brand> getBrandByKeywords(String keywords) {
		// 读取关键字 分词
		List<Word> words = WordSegmenter.seg(keywords);
		/*
		 * for(Word word:words){ System.out.println("分词结果是："+word.toString()); }
		 */
		List<String> keywordList = new ArrayList<String>();
		for (Word word : words) {
			keywordList.add(word.toString());
		}
		
		brandMapper.getBrandByKeywords(keywordList);
		
		
		return null;
	}

}
