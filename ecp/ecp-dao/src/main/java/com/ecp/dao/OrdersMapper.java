package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Orders;

import tk.mybatis.mapper.common.Mapper;

public interface OrdersMapper extends Mapper<Orders> {
	/**
	 * @Description 根据订单时间及订单处理状态查询(特定用户)
	 * @param buyerId  登录用户
	 * @param orderTimeCond 订单时间条件值
	 * @param dealStateCond 合同处理状态条件值
	 * @return  订单列表
	 */
	public List<Orders> selectOrderByOrderTimeAndDealState(@Param("buyerId") long buyerId,
														   @Param("orderTimeCond") int orderTimeCond,
														   @Param("dealStateCond") int dealStateCond);
	/**
	 * @Description 根据订单时间及订单处理状态查询（所有用户）
	 * @param orderTimeCond 订单时间条件值
	 * @param dealStateCond 合同处理状态条件值
	 * @return  订单列表
	 */
	public List<Orders> selectAllOrderByOrderTimeAndDealState(@Param("orderTimeCond") int orderTimeCond,
			   												@Param("dealStateCond") int dealStateCond);
	
	
	/**
	 * @Description 以订单中字段为查询条件
	 * @param orderTimeCond  订单时间条件值
	 * @param dealStateCond  合同处理状态条件值
	 * @param searchTypeValue 查询条件类型
	 * @param condValue  查询条件值
	 * @return
	 * 注:现在只作为后台查询订单生成合同时使用
	 */
	public List<Map<String,Object>> selectOrderBySelfField(@Param("orderTimeCond") int orderTimeCond, 
														   @Param("dealStateCond") int dealStateCond, 
														   @Param("searchTypeValue") int searchTypeValue,
														   @Param("condValue")	String condValue);
	
	/**
	 * @Description 以订单所关联的代理商表字段为查询条件
	 * @param orderTimeCond  订单时间条件值
	 * @param dealStateCond  合同处理状态条件值
	 * @param searchTypeValue  查询条件类型
	 * @param condValue  查询条件值
	 * @return
	 * 注:现在只作为后台查询订单生成合同时使用
	 */
	public List<Map<String,Object>> selectOrderByAgent(@Param("orderTimeCond") int orderTimeCond, 
													   @Param("dealStateCond") int dealStateCond, 
													   @Param("searchTypeValue") int searchTypeValue,
													   @Param("condValue")	String condValue);
	
}