package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustomerLevel;
import com.ecp.service.IBaseService;

public interface ICustomerLevelService extends IBaseService<CustomerLevel, Long> {
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int logicDelById(Long id);
	
	/**
	 * 查询未删除的客户级别
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> map);
	
	/**
	 * 根据客户类型查询
	 * @param typeId	客户类型ID
	 * @return
	 */
	public List<CustomerLevel> getList(Long typeId);
	
}
