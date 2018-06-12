package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.SystemConfigMapper;
import com.ecp.entity.SystemConfig;
import com.ecp.service.back.ISystemConfigService;
import com.ecp.service.impl.AbstractBaseService;

@Service("systemConfigServiceBean")
public class SystemConfigServiceImpl extends AbstractBaseService<SystemConfig, Long> implements ISystemConfigService {

	private SystemConfigMapper systemConfigMapper;

	/**
	 * @param systemConfigMapper the systemConfigMapper to set
	 * set方式注入
	 */
	public void setSystemConfigMapper(SystemConfigMapper systemConfigMapper) {
		this.systemConfigMapper = systemConfigMapper;
		this.setMapper(systemConfigMapper);
	}

	@Override
	public SystemConfig getByConfigName(String configName) {
		SystemConfig config = new SystemConfig();
		config.setConfigName(configName);
		List<SystemConfig> configList = systemConfigMapper.select(config);
		if(configList!=null && configList.size()==1){
			return configList.get(0);
		}
		return null;
	}

	@Override
	public int update(List<SystemConfig> configList) {
		int rows = 0;
		for(SystemConfig temp : configList){
			rows = systemConfigMapper.updateByPrimaryKeySelective(temp);
			if(rows<=0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
		}
		
		return rows;
	}

}
