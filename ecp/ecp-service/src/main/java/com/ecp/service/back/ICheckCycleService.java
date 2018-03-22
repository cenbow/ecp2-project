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
	 * 查询未删除的考核周期
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @return
	 */
	public List<CheckCycle> getList(Map<String, Object> map);
	
}
