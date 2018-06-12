package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.SystemConfig;
import com.ecp.service.IBaseService;

public interface ISystemConfigService extends IBaseService<SystemConfig, Long> {
	
	/**
	 * 根据系统配置名称查询
	 * @param configName
	 * @return
	 */
	public SystemConfig getByConfigName(String configName);
	
	/**
	 * 根据系统配置ID修改系统配置值
	 * @param configList
	 * @return
	 */
	public int update(List<SystemConfig> configList);
	
	
	
}
