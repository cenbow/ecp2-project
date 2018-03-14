package com.ecp.service.front;

import com.ecp.entity.Attribute;
import com.ecp.service.IBaseService;


/**
 * @ClassName IAttributeService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @Date 2017年5月27日 下午4:25:28
 * @version 1.0.0
 */
public interface IAttributeService extends IBaseService<Attribute, Long> {
	
	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param attrId
	 * @return
	 */
	public Attribute getAttributeById(long attrId);
}
