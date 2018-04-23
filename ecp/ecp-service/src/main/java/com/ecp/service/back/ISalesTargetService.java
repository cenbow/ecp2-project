package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.SalesTarget;
import com.ecp.service.IBaseService;

public interface ISalesTargetService extends IBaseService<SalesTarget, Long> {
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int logicDelById(Long id);
	
	/**
	 * 查询未删除的销售指标
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> map);
	
	/**
	 * 查询销售指标
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectSalesTargetMap(Map<String, Object> params);
	
	/**
	 * 保存
	 * @param salesTargetList
	 * @return
	 */
	public int save(List<SalesTarget> salesTargetList);
	
}
