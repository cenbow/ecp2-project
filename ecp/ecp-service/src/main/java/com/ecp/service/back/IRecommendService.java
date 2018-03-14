package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.Recommend;
import com.ecp.service.IBaseService;

public interface IRecommendService extends IBaseService<Recommend, Long> {
	
	/**
	 * 查询所有未删除的推荐
	 * @return
	 */
	public List<Recommend> getAll();
	
	/**
	 * 删除推荐（逻辑删除）
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 批量删除推荐（逻辑删除）
	 * @return
	 */
	public int deleteByIds(List<Long> ids);
	
	
	/**
	 * @Description 按showed状态读取推荐列表
	 * @param showed showed状态: 是否在前端显示（1-显示，2-不显示，默认1）
	 * @return 推荐列表
	 */
	public List<Recommend> getAllByShowed(Integer showed);
}
