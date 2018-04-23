package com.ecp.service.back;

import java.util.List;

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
	 * 根据年度名称查询考核周期
	 * @return
	 */
	public List<CheckCycle> getListByYearName(String yearName);
	
	/**
	 * 根据pid查询
	 * @param pid
	 * @return
	 */
	public List<CheckCycle> getListByPid(Long pid);
	
	public int save(String yearName, String cycleArrJSON);
	
}
