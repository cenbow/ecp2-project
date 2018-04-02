package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.PushmoneyConfig;
import com.ecp.service.IBaseService;

public interface IPushmoneyConfigService extends IBaseService<PushmoneyConfig, Long> {
	
	/**
	 * 根据Map中的条件查询
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> params);
}
