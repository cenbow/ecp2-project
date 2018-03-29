package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.List;

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
	public List<AccountPersonal> getItemsByOrder(long orderId,List<Integer> itemTypeList) {
		//TODO 
		return null;
	}


	@Override
	public int addAccountItem(AccountPersonal accountItem) {
		return accountPersonalMapper.insertSelective(accountItem);
	}


	@Override
	public List<AccountPersonal> getItemsByOrderAndBindUser(long orderId, List<Integer> itemTypeList, long userId,
			List<Long> roleIdList) {
		return accountPersonalMapper.getItemsByOrderAndBindUser(orderId, itemTypeList, userId, roleIdList);
	}
	
	

}
