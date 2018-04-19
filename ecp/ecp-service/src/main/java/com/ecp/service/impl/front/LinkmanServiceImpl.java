package com.ecp.service.impl.front;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.LinkmanMapper;
import com.ecp.entity.Linkman;
import com.ecp.service.front.ILinkmanService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class LinkmanServiceImpl extends AbstractBaseService<Linkman, Long> implements ILinkmanService {
	
	LinkmanMapper linkmanMapper;
	

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	public void setLinkmanMapper(LinkmanMapper mapper) {
		this.linkmanMapper=mapper;
		this.setMapper(mapper);
	}


	@Override
	public List<Linkman> getLinkmanByOrder(long orderId) {
		Linkman rec=new Linkman();
		rec.setDeleted((byte)DeletedType.NO);
		rec.setOrderId(orderId);
		
		return linkmanMapper.select(rec);
		
	}


	@Override
	public int addLinkman(Linkman linkman) {
		linkman.setCreateTime(new Date());
		
		return linkmanMapper.insertSelective(linkman);
	}


	@Override
	public List<Map<String, Object>> getLinkmanByLinkmanName(String name) {
		return linkmanMapper.getLinkmanByLinkmanName(name);
	}


	@Override
	public List<Map<String, Object>> getLinkmanByLinkmanMobile(String mobile) {
		return linkmanMapper.getLinkmanByLinkmanMobile(mobile);
	}


	

}
