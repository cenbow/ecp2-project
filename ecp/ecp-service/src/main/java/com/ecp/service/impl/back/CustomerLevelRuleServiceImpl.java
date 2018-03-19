package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.CustomerLevelRuleMapper;
import com.ecp.entity.CustomerLevelRule;
import com.ecp.service.back.ICustomerLevelRuleService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("customerLevelRuleServiceBean")
public class CustomerLevelRuleServiceImpl extends AbstractBaseService<CustomerLevelRule, Long> implements ICustomerLevelRuleService {

	private CustomerLevelRuleMapper customerLevelRuleMapper;

	/**
	 * @param customerLevelRuleMapper the customerLevelRuleMapper to set
	 * set方式注入
	 */
	public void setCustomerLevelRuleMapper(CustomerLevelRuleMapper customerLevelRuleMapper) {
		this.customerLevelRuleMapper = customerLevelRuleMapper;
		this.setMapper(customerLevelRuleMapper);
	}
	
	/**
	 * 逻辑删除
	 * @see com.ecp.service.back.ICustomerLevelRuleService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			CustomerLevelRule rule = new CustomerLevelRule();
			rule.setId(id);
			//rule.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = customerLevelRuleMapper.updateByPrimaryKeySelective(rule);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 查询未删除的客户级别规则
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @see com.ecp.service.back.ICustomerLevelRuleService#getList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {
		//Example example=new Example(CustomerLevelRule.class);
		//example.createCriteria().andEqualTo("deleted", map.get("deleted").toString());
		List<Map<String, Object>> list=customerLevelRuleMapper.getListMap(map);
		return list;
	}

}
