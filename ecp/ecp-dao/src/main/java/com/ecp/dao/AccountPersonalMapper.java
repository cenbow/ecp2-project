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
	
	public int logicDeleteByCompanyItemId(@Param("companyItemId") long companyItemId);
	
	public List<Map<String, Object>> getUserByAccountCompanyId(@Param("accountCompanyId") long accountCompanyId);
	
	/** 
	* @Title: getItemsByDateAndUser 
	* @Description: 根据时间及用户查询会计条目. 
	* @param @param startDateYear
	* @param @param startDateMonth
	* @param @param endDateYear
	* @param @param endDateMonth
	* @param @param userId
	* @param @param roleId 查询四项费用时角色为null
	* @param @param itemTypeList  会计条目类型
	* @param @return     
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getItemsByDateAndUser(
			@Param("startDateYear") String startDateYear, 
			@Param("startDateMonth") String startDateMonth,
			@Param("endDateYear") String endDateYear, 
			@Param("endDateMonth") String endDateMonth, 
			@Param("userId") long userId, 
			@Param("roleId") Long roleId, 
			@Param("itemTypeList") List<Integer> itemTypeList);
	
}