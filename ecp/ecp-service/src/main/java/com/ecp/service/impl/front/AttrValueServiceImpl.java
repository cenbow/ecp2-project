package com.ecp.service.impl.front;

import org.springframework.stereotype.Service;

import com.ecp.dao.AttributeValueMapper;
import com.ecp.entity.AttributeValue;
import com.ecp.service.front.IAttrValueService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class AttrValueServiceImpl extends AbstractBaseService<AttributeValue, Long> implements IAttrValueService {
	
	AttributeValueMapper attributeValueMapper;
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setAttributeValueMapper(AttributeValueMapper attributeValueMapper) { 
		this.attributeValueMapper=attributeValueMapper;
		this.setMapper(attributeValueMapper);
	}

	@Override
	public AttributeValue getAttributeValueById(long attrId, long valueId) {
		AttributeValue record=new AttributeValue();
		record.setAttrId(attrId);
		record.setValueId(valueId);
		return attributeValueMapper.selectOne(record);		
	}	


	
	

}
