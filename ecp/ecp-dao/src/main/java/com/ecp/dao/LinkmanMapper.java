package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Linkman;

import tk.mybatis.mapper.common.Mapper;

public interface LinkmanMapper extends Mapper<Linkman> {
	public List<Map<String, Object>> getLinkmanByLinkmanName(@Param("name") String name);
	public List<Map<String, Object>> getLinkmanByLinkmanMobile(@Param("mobile")String mobile);
}