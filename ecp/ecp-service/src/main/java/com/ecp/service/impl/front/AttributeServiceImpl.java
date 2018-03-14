package com.ecp.service.impl.front;

import org.springframework.stereotype.Service;

import com.ecp.dao.AttributeMapper;
import com.ecp.entity.Attribute;
import com.ecp.service.front.IAttributeService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class AttributeServiceImpl extends AbstractBaseService<Attribute, Long> implements IAttributeService {

	//private UserMapper userMapper;  //已经自抽象类继承了变更此处不必再声明一个变量
	
	AttributeMapper attributeMapper;

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	
	public void setAttributeMapper(AttributeMapper attributeMapper) {
		this.attributeMapper=attributeMapper;
		this.setMapper(attributeMapper);
	}

	@Override
	public Attribute getAttributeById(long attrId) {
		Attribute record=new Attribute();
		record.setAttrId(attrId);
		return attributeMapper.selectOne(record);		
	}

	

}
