package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.CheckCycleMapper;
import com.ecp.entity.CheckCycle;
import com.ecp.service.back.ICheckCycleService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("checkCycleServiceBean")
public class CheckCycleServiceImpl extends AbstractBaseService<CheckCycle, Long> implements ICheckCycleService {

	private CheckCycleMapper checkCycleMapper;

	/**
	 * @param checkCycleMapper the checkCycleMapper to set
	 * set方式注入
	 */
	public void setCheckCycleMapper(CheckCycleMapper checkCycleMapper) {
		this.checkCycleMapper = checkCycleMapper;
		this.setMapper(checkCycleMapper);
	}
	
	/**
	 * 逻辑删除
	 * @see com.ecp.service.back.ICustomerTypeService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			CheckCycle cycle = new CheckCycle();
			cycle.setId(id);
			//cycle.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = checkCycleMapper.updateByPrimaryKeySelective(cycle);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 查询未删除的考核周期
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @see com.ecp.service.back.ICheckCycleService#getList(java.util.Map)
	 */
	@Override
	public List<CheckCycle> getList(Map<String, Object> map) {
		Example example=new Example(CheckCycle.class);
		//example.createCriteria().andEqualTo("deleted", map.get("deleted").toString());
		List<CheckCycle> list=checkCycleMapper.selectByExample(example);
		return list;
	}

}
