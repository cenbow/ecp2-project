package com.ecp.service.front;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecp.bean.AddSkuToOrderBean;
import com.ecp.entity.OrderItems;
import com.ecp.service.IBaseService;


public interface IOrderItemService extends IBaseService<OrderItems, Long> {
	
	
	/**
	 * @Description 向订单中增加sku条目
	 * @param itemList
	 * @param orderId
	 */
	public void addItemIntoOrder(List<AddSkuToOrderBean> itemList,String orderId);
	
	/** 
		* @Title: addItemIntoOrder 
		* @Description: 向订单中增加sku条目 
		* @param @param itemList sku数据(来自于购物车)
		* @param @param skuAppendAttrList sku附加的属性(价格阈值,硬成本,是否方案性产品),与itemList中的数据一一对应
		* @param @param orderId    订单No 
		* @return void    返回类型 
		* @throws 
	*/
	public void addItemIntoOrder(List<AddSkuToOrderBean> itemList,List<HashMap<String,Object>> skuAppendAttrList, String orderId);
	
	
	
	/**
	 * @Description 根据订单id读取订单数据
	 * @param orderId
	 * @return
	 */
	public List<Map<String,String>>  selectItemsByOrderId(String orderId);
	
	/**
	 * 根据订单编号查询订单总金额
	 * @param orderNo
	 * @return
	 * 		返回订单总金额（BigDecimal）
	 */
	public BigDecimal getOrderAmountByNo(String orderNo);
	
	/**
	 * 查询产品销售清单统计
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getItemSalesStats(Map<String, Object> params);
	
}
