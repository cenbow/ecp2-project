package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.CustomerTypeMapper;
import com.ecp.entity.CustomerType;
import com.ecp.service.back.ICustomerTypeService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("customerTypeServiceBean")
public class CustomerTypeServiceImpl extends AbstractBaseService<CustomerType, Long> implements ICustomerTypeService {

	private CustomerTypeMapper customerTypeMapper;

	/**
	 * @param customerTypeMapper the customerTypeMapper to set
	 * set方式注入
	 */
	public void setCustomerTypeMapper(CustomerTypeMapper customerTypeMapper) {
		this.customerTypeMapper = customerTypeMapper;
		this.setMapper(customerTypeMapper);
	}
	
	/**
	 * 逻辑删除
	 * @see com.ecp.service.back.ICustomerTypeService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			CustomerType type = new CustomerType();
			type.setId(id);
			//type.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = customerTypeMapper.updateByPrimaryKeySelective(type);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 查询未删除的客户类型
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @see com.ecp.service.back.ICustomerTypeService#getList(java.util.Map)
	 */
	@Override
	public List<CustomerType> getList(Map<String, Object> map) {
		Example example=new Example(CustomerType.class);
		example.createCriteria().andEqualTo("deleted", map.get("deleted").toString());
		List<CustomerType> list=customerTypeMapper.selectByExample(example);
		return list;
	}

}
