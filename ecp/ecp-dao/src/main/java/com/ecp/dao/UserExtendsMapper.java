package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.UserExtends;
import tk.mybatis.mapper.common.Mapper;

public interface UserExtendsMapper extends Mapper<UserExtends> {
	
	public List<UserExtends> searchUserAgent(
			 @Param("searchTypeValue") int searchTypeValue,
			 @Param("condValue")	String condValue, 
			 @Param("provinceName") String provinceName, 
			 @Param("cityName") 	String cityName, 
			 @Param("countyName")	String countyName,
			 @Param("agentIdList") List<Map<String, Object>> agentIdList,
			 @Param("auditStatus") byte auditStatus);
}