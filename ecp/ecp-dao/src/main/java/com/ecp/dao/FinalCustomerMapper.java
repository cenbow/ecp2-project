package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.FinalCustomer;

import tk.mybatis.mapper.common.Mapper;

public interface FinalCustomerMapper extends Mapper<FinalCustomer> {
	public List<Map<String, Object>> getFinalCustomerByOrganizationName(@Param("organizationName") String organizationName);
}