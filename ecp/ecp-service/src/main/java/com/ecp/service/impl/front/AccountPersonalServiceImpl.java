package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.AccountPersonalMapper;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class AccountPersonalServiceImpl extends AbstractBaseService<AccountPersonal, Long> implements IAccountPersonalService {
	
	AccountPersonalMapper accountPersonalMapper;
	
	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	public void setAccountPersonalMapper(AccountPersonalMapper mapper) {
		this.accountPersonalMapper=mapper;
		this.setMapper(mapper);
	}


	@Override
	public List<AccountCompany> getItemsByOrder(long orderId, String orderNo, List<Integer> itemTypeList) {
		
		return null;
	}


	@Override
	public int addAccountItem(long orderId, String orderNo, int itemType, BigDecimal amount) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Map<String, Object>> selectPerformanceMap(Map<String, Object> params) {
		return accountPersonalMapper.selectPerformanceMap(params);
	}
	
	

}
