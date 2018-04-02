package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.ecp.entity.AccountPersonal;
import tk.mybatis.mapper.common.Mapper;

public interface AccountPersonalMapper extends Mapper<AccountPersonal> {
	
	/**
	 * 根据条件查询业绩
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectPerformanceMap(Map<String, Object> params);

	public List<AccountPersonal> getItemsByOrderAndBindUser(@Param("orderId") long orderId, 
			   @Param("itemTypeList") List<Integer> itemTypeList, 
			   @Param("userId") long userId,
			   @Param("roleIdList") List<Long> roleIdList);
	
	
	public List<AccountPersonal> selectItems(
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