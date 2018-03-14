package com.ecp.service.impl.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.AttributeMapper;
import com.ecp.entity.Attribute;
import com.ecp.service.back.IAttributeService;
import com.ecp.service.impl.AbstractBaseService;

@Service("attributeServiceBean")
public class AttributeServiceImpl extends AbstractBaseService<Attribute, Long> implements IAttributeService {

	private AttributeMapper attributeMapper;

	/**
	 * @param attributeMapper the attributeMapper to set
	 * set方式注入
	 */
	public void setAttributeMapper(AttributeMapper attributeMapper) {
		this.attributeMapper = attributeMapper;
		this.setMapper(attributeMapper);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.ecp.service.IAttributeService#selectByCid(java.lang.Long)
	 * 根据类目ID查询属性
	 */
	@Override
	public List<Map<String, Object>> selectByCid(Long cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(cid!=null && cid>0){
			map.put("cid", cid);
		}else{
			return null;
		}
		return attributeMapper.selectByCondition(map);
	}

}
