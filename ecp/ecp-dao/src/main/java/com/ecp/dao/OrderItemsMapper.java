package com.ecp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.OrderItems;

import tk.mybatis.mapper.common.Mapper;

public interface OrderItemsMapper extends Mapper<OrderItems> {
	
	public List<Map<String,String>> selectItemsByOrderId(@Param("orderId") String orderId);
	
	/**
	 * 根据订单编号查询订单总金额
	 * @param orderNo
	 * @return
	 * 		返回订单总金额（BigDecimal）
	 */
	public BigDecimal getOrderAmountByNo(@Param("orderNo") String orderNo);
	
}