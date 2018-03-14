package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Item;

import tk.mybatis.mapper.common.Mapper;

public interface ItemMapper extends Mapper<Item> {
	
	/**
	 * @Description 通过品牌、属性值进行查询
	 * @param brands
	 * @param attrValPairs
	 * @return
	 */
	public List<Item> getItemByBrandAndAttr(@Param("cid") Long cid, 
											@Param("brands") List<Long> brands, 
											@Param("attrValPairs") List<String> attrValPairs,
											@Param("itemStatus") Integer itemStatus);
	
	
	/**
	 * @Description 通过品牌、cids查询
	 * @param brands
	 * @param cids
	 * @return
	 */
	public List<Item> getItemByBrandAndCid(@Param("brands") List<Long> brands,
											@Param("cids") List<Long> cids,
											@Param("itemStatus") Integer itemStatus);
	
	
	/**
	 * @Description 按SPU中的关键字、品牌、类目ID查询
	 * @param keywordList
	 * @param brands
	 * @param cids
	 * @return
	 */
	public List<Item> getItemByKeywordsAndBrandAndCid(@Param("keywords") List<String> keywordList,
													  @Param("brands") List<Long> brands,
													  @Param("cids") List<Long> cids,
													  @Param("itemStatus") Integer itemStatus);
	
	
	/**
	 * @Description 查询SPU keywords item_name
	 * @param keywordList
	 * @return
	 */
	public List<Item> getItemByKeywords(@Param("keywords") List<String> keywordList,
										@Param("itemStatus") Integer itemStatus);
	
	
	/**
	 * @Description 根据品牌id获取 SPU列表
	 * @param ids
	 * @return
	 */
	public List<Item> getItemByBrandIds(@Param("ids") List<Long> ids,
										@Param("itemStatus") Integer itemStatus);
	
	
	/**
	 * 根据条件查询商品
	 * @param map
	 * 		map为null时查询所有商品
	 * @return
	 */
	List<Map<String, Object>> selectItemsByCondition(Map<String, Object> map);

}