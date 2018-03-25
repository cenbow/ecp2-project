package com.ecp.service.impl.front;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.FinalCustomerMapper;
import com.ecp.entity.FinalCustomer;
import com.ecp.service.front.IFinalCustomerService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class FinalCustomerServiceImpl extends AbstractBaseService<FinalCustomer, Long> implements IFinalCustomerService {
	
	FinalCustomerMapper finalCustomerMapper;
	

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	public void setFinalCustomerMapper(FinalCustomerMapper mapper) {
		this.finalCustomerMapper=mapper;
		this.setMapper(mapper);
	}


	@Override
	public List<FinalCustomer> getFinalCustomerByOrder(long orderId) {
		FinalCustomer rec=new FinalCustomer();
		rec.setOrderId(orderId);
		
		return finalCustomerMapper.select(rec);
		
	}


	@Override
	public int addFinalCustomer(FinalCustomer finalCustomer) {
		finalCustomer.setCreateTime(new Date());
		return finalCustomerMapper.insertSelective(finalCustomer);
	}


	

}
