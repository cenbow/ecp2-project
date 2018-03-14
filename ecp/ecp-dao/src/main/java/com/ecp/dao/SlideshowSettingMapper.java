package com.ecp.dao;

import java.util.List;

import com.ecp.entity.SlideshowSetting;

import tk.mybatis.mapper.common.Mapper;

public interface SlideshowSettingMapper extends Mapper<SlideshowSetting> {
	
	/**
	 * 根据id集合删除（逻辑删除）
	 * @param ids
	 * @return
	 */
	public int deleteByIds(List<Long> ids);
	
}