package com.ecp.dao;

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
	
}