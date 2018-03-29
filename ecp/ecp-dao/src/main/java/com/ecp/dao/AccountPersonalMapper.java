package com.ecp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
import tk.mybatis.mapper.common.Mapper;

public interface AccountPersonalMapper extends Mapper<AccountPersonal> {
	
	public List<AccountPersonal> getItemsByOrderAndBindUser(@Param("orderId") long orderId, 
			   @Param("itemTypeList") List<Integer> itemTypeList, 
			   @Param("userId") long userId,
			   @Param("roleIdList") List<Long> roleIdList);
	
}