package com.ecp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.AccountCompany;

import tk.mybatis.mapper.common.Mapper;

public interface AccountCompanyMapper extends Mapper<AccountCompany> {
	public List<AccountCompany> getItemsByOrder(@Param("orderId") long orderId, 
												@Param("orderNo") String orderNo,
												@Param("itemTypeList") List<Integer> itemTypeList);
	
	public List<AccountCompany> getItemsByOrderAndBindUser(@Param("orderId") long orderId, 
														   @Param("itemTypeList") List<Integer> itemTypeList, 
														   @Param("userId") long userId,
														   @Param("roleIdList") List<Long> roleIdList);
	
	public List<AccountCompany> selectItems(
			@Param("orderTimeCond") int orderTimeCond, 
			@Param("dealStateCond") int dealStateCond, 
			@Param("searchTypeValue") int searchTypeValue,
			@Param("condValue") String condValue, 
			@Param("provinceName") String provinceName, 
			@Param("cityName") String cityName, 
			@Param("countyName") String countyName,
			@Param("agentIdList") List<Map<String, Object>> agentIdList, 
			@Param("itemTypeList") List<Integer> itemTypeList, 
			@Param("userId") long userId,
			@Param("roleIdList") List<Long> roleIdList);
	
	/**
	 * 根据Map参数查询公司账本
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectAccountCompanyMap(Map<String, Object> params);

	public BigDecimal getAmountByOrderId(@Param("orderId") long orderId,@Param("accountItemType") int accountItemType);
	
	
	public BigDecimal searchAccountItemAmount(
			@Param("orderTimeCond") int orderTimeCond, 
			@Param("dealStateCond") int dealStateCond, 
			@Param("searchTypeValue") int searchTypeValue,
			@Param("condValue") String condValue, 
			@Param("provinceName") String provinceName, 
			@Param("cityName") String cityName, 
			@Param("countyName") String countyName,
			@Param("agentIdList") List<Map<String, Object>> agentIdList, 
			@Param("itemTypeList") List<Integer> itemTypeList, 
			@Param("userId") long userId,
			@Param("roleIdList") List<Long> roleIdList);
	
	
	public List<Map<String, Object>> getItemsByDateAndUser(
			@Param("startDateYear") String startDateYear, 
			@Param("startDateMonth") String startDateMonth,
			@Param("endDateYear") String endDateYear, 
			@Param("endDateMonth") String endDateMonth, 
			@Param("userId") long userId, 
			@Param("itemTypeList") List<Integer> itemTypeList);

	
}