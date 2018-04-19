package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CheckCycle;
import com.ecp.service.IBaseService;

public interface ICheckCycleService extends IBaseService<CheckCycle, Long> {
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int logicDelById(Long id);
	
	/**
	 * 根据条件查询考核周期
	 * @return
	 */
	public List<CheckCycle> getList(Map<String, Object> map);
	
	public int save(String yearName, String cycleArrJSON);
	
}
