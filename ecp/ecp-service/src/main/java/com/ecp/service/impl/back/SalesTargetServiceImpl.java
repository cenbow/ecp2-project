package com.ecp.service.impl.back;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.SalesTargetMapper;
import com.ecp.entity.SalesTarget;
import com.ecp.service.back.ISalesTargetService;
import com.ecp.service.impl.AbstractBaseService;

@Service("salesTargetServiceBean")
public class SalesTargetServiceImpl extends AbstractBaseService<SalesTarget, Long> implements ISalesTargetService {

	private SalesTargetMapper salesTargetMapper;

	/**
	 * @param salesTargetMapper the salesTargetMapper to set
	 * set方式注入
	 */
	public void setSalesTargetMapper(SalesTargetMapper salesTargetMapper) {
		this.salesTargetMapper = salesTargetMapper;
		this.setMapper(salesTargetMapper);
	}
	
	/**
	 * 逻辑删除
	 * @see com.ecp.service.back.ISalesTargetService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			SalesTarget salesTarget = new SalesTarget();
			salesTarget.setId(id);
			//salesTarget.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = salesTargetMapper.updateByPrimaryKeySelective(salesTarget);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 查询未删除的销售指标
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @see com.ecp.service.back.ISalesTargetService#getList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> map) {
		List<Map<String, Object>> list=salesTargetMapper.getListMap(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectSalesTargetMap(Map<String, Object> params) {
		return salesTargetMapper.selectSalesTargetMap(params);
	}

}
