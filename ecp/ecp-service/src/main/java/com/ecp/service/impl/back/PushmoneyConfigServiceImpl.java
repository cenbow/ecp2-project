package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.PushmoneyConfigMapper;
import com.ecp.entity.PushmoneyConfig;
import com.ecp.service.back.IPushmoneyConfigService;
import com.ecp.service.impl.AbstractBaseService;

@Service("pushmoneyConfigServiceBean")
public class PushmoneyConfigServiceImpl extends AbstractBaseService<PushmoneyConfig, Long> implements IPushmoneyConfigService {

	private PushmoneyConfigMapper pushmoneyConfigMapper;

	/**
	 * @param pushmoneyConfigMapper the pushmoneyConfigMapper to set
	 * set方式注入
	 */
	public void setPushmoneyConfigMapper(PushmoneyConfigMapper pushmoneyConfigMapper) {
		this.pushmoneyConfigMapper = pushmoneyConfigMapper;
		this.setMapper(pushmoneyConfigMapper);
	}

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> params) {
		return pushmoneyConfigMapper.getList(params);
	}
	
}
