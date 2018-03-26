package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.AccountCompanyMapper;
import com.ecp.entity.AccountCompany;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class AccountCompanyServiceImpl extends AbstractBaseService<AccountCompany, Long> implements IAccountCompanyService {
	
	AccountCompanyMapper accountCompanyMapper;
	

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	public void setAccountCompanyMapper(AccountCompanyMapper mapper) {
		this.accountCompanyMapper=mapper;
		this.setMapper(mapper);
	}


	@Override
	public List<AccountCompany> getItemsByOrder(long orderId, String orderNo, List<Integer> itemTypeList) {
		return accountCompanyMapper.getItemsByOrder(orderId,orderNo,itemTypeList);
	}


	@Override
	public int addAccountItem(AccountCompany accountItem) {
		return accountCompanyMapper.insertSelective(accountItem);
	}

	

}
