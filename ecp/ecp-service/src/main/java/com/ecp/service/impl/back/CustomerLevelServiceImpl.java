package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.CustomerLevelMapper;
import com.ecp.entity.CustomerLevel;
import com.ecp.service.back.ICustomerLevelService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("customerLevelServiceBean")
public class CustomerLevelServiceImpl extends AbstractBaseService<CustomerLevel, Long> implements ICustomerLevelService {

	private CustomerLevelMapper customerLevelMapper;

	/**
	 * @param customerLevelMapper the customerLevelMapper to set
	 * set方式注入
	 */
	public void setCustomerLevelMapper(CustomerLevelMapper customerLevelMapper) {
		this.customerLevelMapper = customerLevelMapper;
		this.setMapper(customerLevelMapper);
	}
	
	/**
	 * 逻辑删除
	 * @see com.ecp.service.back.ICustomerLevelService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			CustomerLevel level = new CustomerLevel();
			level.setId(id);
			//level.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = customerLevelMapper.updateByPrimaryKeySelective(level);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 查询未删除的客户级别
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @see com.ecp.service.back.ICustomerLevelService#getList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {
		//Example example=new Example(CustomerLevel.class);
		//example.createCriteria().andEqualTo("deleted", map.get("deleted").toString());
		List<Map<String, Object>> list=customerLevelMapper.getListMap(map);
		return list;
	}

	@Override
	public List<CustomerLevel> getList(Long typeId) {
		Example example=new Example(CustomerLevel.class);
		example.createCriteria().andEqualTo("customerTypeId", typeId);
		return customerLevelMapper.selectByExample(example);
	}

}
