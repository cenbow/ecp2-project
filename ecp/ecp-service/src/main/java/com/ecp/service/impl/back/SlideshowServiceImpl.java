package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.SlideshowSettingMapper;
import com.ecp.entity.Recommend;
import com.ecp.entity.SlideshowSetting;
import com.ecp.service.back.ISlideshowService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("slideshowServiceBean")
public class SlideshowServiceImpl extends AbstractBaseService<SlideshowSetting, Long> implements ISlideshowService {

	private SlideshowSettingMapper slideshowSettingMapper;

	/**
	 * @param slideshowSettingMapper the slideshowSettingMapper to set
	 * set方式注入
	 */
	public void setSlideshowSettingMapper(SlideshowSettingMapper slideshowSettingMapper) {
		this.slideshowSettingMapper = slideshowSettingMapper;
		this.setMapper(slideshowSettingMapper);
	}

	/**
	 * @see com.ecp.service.back.ISlideshowService#getAll()
	 * 查询所有未删除的数据
	 */
	@Override
	public List<SlideshowSetting> getAll() {
		Example example = new Example(Recommend.class);
		example.createCriteria().andEqualTo("deleted", DeletedType.NO);
		example.setOrderByClause("created desc");
		return slideshowSettingMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.ISlideshowService#deleteById(java.lang.Long)
	 * 删除（逻辑删除）
	 */
	@Override
	public int deleteById(Long id) {
		SlideshowSetting slideshow = new SlideshowSetting();
		slideshow.setId(id);
		slideshow.setDeleted(DeletedType.YES);
		return slideshowSettingMapper.updateByPrimaryKeySelective(slideshow);
	}

	/**
	 * @see com.ecp.service.back.ISlideshowService#deleteByIds(java.util.List)
	 * 批量删除（逻辑删除）
	 */
	@Override
	public int deleteByIds(List<Long> ids) {
		return slideshowSettingMapper.deleteByIds(ids);
	}

	/**
	 * Description:按showed状态读取轮播图列表
	 * @see com.ecp.service.back.ISlideshowService#getAllByShowed(java.lang.Integer)
	 */
	@Override
	public List<SlideshowSetting> getAllByShowed(Integer showed) {
		SlideshowSetting record=new SlideshowSetting();
		record.setShowed(showed);
		return slideshowSettingMapper.select(record);
		
	}

}
