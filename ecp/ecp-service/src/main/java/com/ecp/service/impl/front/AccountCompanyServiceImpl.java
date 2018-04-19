package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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


	@Override
	public List<AccountCompany> getItemsByOrderAndBindUser(long orderId, List<Integer> itemTypeList, long userId,
			List<Long> roleIdList) {
		
		return accountCompanyMapper.getItemsByOrderAndBindUser(orderId,itemTypeList,userId,roleIdList);
	}


	@Override
	public List<AccountCompany> selectItems(
			int orderTimeCond, 
			int dealStateCond, 
			int searchTypeValue,
			String condValue, 
			String provinceName, String cityName, String countyName,
			List<Map<String, Object>> agentIdList, 
			List<Integer> itemTypeList, 
			long bindedUserId,
			List<Long> roleIdList) {
		
		return accountCompanyMapper.selectItems(
				-orderTimeCond, 
				dealStateCond, 
				searchTypeValue,
				condValue,  
				provinceName,  
				cityName,  
				countyName,
				agentIdList,				
				itemTypeList, 
				bindedUserId,
				roleIdList
				);
	}


	@Override
	public List<Map<String, Object>> selectAccountCompanyMap(Map<String, Object> params) {
		return accountCompanyMapper.selectAccountCompanyMap(params);
	}
	public BigDecimal getAmountByOrderId(long orderId, int accountItemType) {
		return accountCompanyMapper.getAmountByOrderId(orderId,accountItemType);
	}


	@Override
	public BigDecimal searchAccountItemAmount(int orderTimeCond, int dealStateCond, int searchTypeValue,
			String condValue, String provinceName, String cityName, String countyName,
			List<Map<String, Object>> agentIdList, List<Integer> itemTypeList, long bindedUserId,
			List<Long> roleIdList) {
		
		return accountCompanyMapper.searchAccountItemAmount(
				-orderTimeCond, 
				dealStateCond, 
				searchTypeValue,
				condValue,  
				provinceName,  
				cityName,  
				countyName,
				agentIdList,				
				itemTypeList, 
				bindedUserId,
				roleIdList
				);
	}


	@Override
	public List<Map<String, Object>> getItemsByDateAndUser(String startDateYear, String startDateMonth,
			String endDateYear, String endDateMonth, long userId, List<Integer> itemTypeList) {
		return accountCompanyMapper.getItemsByDateAndUser(startDateYear, startDateMonth,
				 endDateYear,  endDateMonth,  userId, itemTypeList);
	}

}
