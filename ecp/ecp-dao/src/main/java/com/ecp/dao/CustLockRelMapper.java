package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.CustLockRel;

import tk.mybatis.mapper.common.Mapper;

public interface CustLockRelMapper extends Mapper<CustLockRel> {
	public List<Map<String,Object>> getSalesByAgentId(@Param("agentId") long agentId,@Param("roleName")String roleName);
	public List<Map<String,Object>> getSales(@Param("roleName") String roleName);
}