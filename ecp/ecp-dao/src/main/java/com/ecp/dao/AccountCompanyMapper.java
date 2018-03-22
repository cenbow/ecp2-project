package com.ecp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.AccountCompany;

import tk.mybatis.mapper.common.Mapper;

public interface AccountCompanyMapper extends Mapper<AccountCompany> {
	public List<AccountCompany> getItemsByOrder(@Param("orderId") long orderId, 
												@Param("orderNo") String orderNo,
												@Param("itemTypeList") List<Integer> itemTypeList);
}