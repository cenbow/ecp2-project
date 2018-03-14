package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.SlideshowSetting;
import com.ecp.service.IBaseService;

public interface ISlideshowService extends IBaseService<SlideshowSetting, Long> {
	
	/**
	 * 查询所有未删除的数据
	 * @return
	 */
	public List<SlideshowSetting> getAll();
	
	/**
	 * @Description 按showed状态读取轮播图列表
	 * @param showed showed状态: 是否在前端显示（1-显示，2-不显示，默认1）
	 * @return  轮播图列表
	 */
	public List<SlideshowSetting> getAllByShowed(Integer showed);
	
	
	/**
	 * 删除（逻辑删除）
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 批量删除（逻辑删除）
	 * @return
	 */
	public int deleteByIds(List<Long> ids);
}
