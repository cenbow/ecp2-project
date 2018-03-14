package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ecp.entity.Item;
import com.ecp.service.IBaseService;

public interface IItemService extends IBaseService<Item, Long> {
	
	/**
	 * 根据条件查询列表（条件为空时查询所有）
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectItemsByCondition(Map<String, Object> map);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String ids);
	
	/**
	 * 保存商品
	 * @param request
	 * @param item
	 * @param skuJson
	 * @param skuPriceJson
	 * @param skuShortSpec
	 * @param skuSpec
	 * @return
	 */
	public int saveItem(HttpServletRequest request, Item item, String skuJson, String skuPriceJson, String skuShortSpec, String skuSpec);
	
	/**
	 * 修改商品
	 * @param request
	 * @param item
	 * @param skuJson
	 * @param skuPriceJson
	 * @param isSaveSku
	 * @param skuShortSpec
	 * @param skuSpec
	 * @return
	 */
	public int updateItem(HttpServletRequest request, Item item, String skuJson, String skuPriceJson, boolean isSaveSku, String skuShortSpec, String skuSpec);
	
	/**
	 * 批量修改商品状态
	 * @param itemIds
	 * @param itemStatus
	 * @return
	 */
	public int updateStatusBatchByIds(String itemIds, Integer itemStatus);
	
}
