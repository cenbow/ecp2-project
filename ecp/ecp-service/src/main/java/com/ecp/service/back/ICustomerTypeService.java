package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustomerType;
import com.ecp.service.IBaseService;

public interface ICustomerTypeService extends IBaseService<CustomerType, Long> {
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int logicDelById(Long id);
	
	/**
	 * 查询未删除的客户类型
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @return
	 */
	public List<CustomerType> getList(Map<String, Object> map);
	
}
