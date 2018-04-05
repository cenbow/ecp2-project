package com.ecp.service.impl.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecp.dao.UserExtendsMapper;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.front.IAgentService;
import com.ecp.service.front.IUserAgentService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class UserAgentServiceImpl extends AbstractBaseService<UserExtends, Long> implements IUserAgentService {
	
	UserExtendsMapper userExtendsMapper;
	
	@Autowired
	IAgentService userService;   //用户服务

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	
	public void setUserExtendsMapper(UserExtendsMapper userExtendsMapper) {
		this.userExtendsMapper=userExtendsMapper;
		this.setMapper(userExtendsMapper);
	}

	
	@Override
	public int addUserAgent(UserExtends agent) {
		return userExtendsMapper.insertSelective(agent);
	}

	@Override
	public List<UserExtends> getAllUserAgent() {
		return userExtendsMapper.selectAll();
	}


	@Override
	public UserExtends getUserAgentByUserId(long userId) {
		User user=userService.selectByPrimaryKey(userId);
		long primaryAccountNo=user.getId();
		if(user.getParentId()!=null && user.getParentId()!=0)  //下单者是子帐号
		{
			primaryAccountNo=user.getParentId();
		}
		
		UserExtends record=new UserExtends();
		record.setUserId(primaryAccountNo);
		return userExtendsMapper.selectOne(record);
		
	}


	@Override
	public List<UserExtends> searchUserAgent(int searchTypeValue, String condValue) {
		List<UserExtends> resultList=null;
		Example example =new Example(UserExtends.class);
		Map<Integer,String> map=new HashMap<Integer, String>();
		map.put(1, "companyName");
		map.put(2, "artificialPersonName");
		map.put(3, "contactPhone");
		
		switch(searchTypeValue){
		case 0:   	//没有选择条件
			resultList=userExtendsMapper.selectAll();
			break;
		case 1:
		case 2:
		case 3:    	
			example.createCriteria().andLike(map.get(searchTypeValue), condValue);
			resultList=userExtendsMapper.selectByExample(example);
			break;
		default:
			resultList=userExtendsMapper.selectAll();
			break;
		}
		
		return resultList;
		
	}


	@Override
	public List<UserExtends> searchUserAgent(int searchTypeValue, String condValue, String provinceName,
			String cityName, String countyName) {
		List<UserExtends> resultList=null;
		Example example =new Example(UserExtends.class);
		Map<Integer,String> map=new HashMap<Integer, String>();
		map.put(1, "companyName");
		map.put(2, "artificialPersonName");
		map.put(3, "contactPhone");
		
		Criteria criteria=example.createCriteria();
		switch(searchTypeValue){
		case 0:   	//没有选择条件类型时			
			if(!StringUtil.isEmpty(provinceName)){				
				criteria.andEqualTo("province", provinceName);				
			}
			if(!StringUtil.isEmpty(cityName)){
				criteria.andEqualTo("city", cityName);
			}
			if(!StringUtil.isEmpty(countyName)){
				criteria.andEqualTo("county", countyName);
			}			
			resultList=userExtendsMapper.selectByExample(example);
			break;
		case 1:
		case 2:
		case 3:
			//区域条件			
			if(!StringUtil.isEmpty(provinceName)){
				criteria.andEqualTo("province", provinceName);
			}
			if(!StringUtil.isEmpty(cityName)){
				criteria.andEqualTo("city", cityName);
			}
			if(!StringUtil.isEmpty(countyName)){
				criteria.andEqualTo("county", countyName);
			}
			
			
			//条件类型
			criteria.andLike(map.get(searchTypeValue), condValue);
			resultList=userExtendsMapper.selectByExample(example);
			break;
		default:
			if(!StringUtil.isEmpty(provinceName)){				
				criteria.andEqualTo("province", provinceName);				
			}
			if(!StringUtil.isEmpty(cityName)){
				criteria.andEqualTo("city", cityName);
			}
			if(!StringUtil.isEmpty(countyName)){
				criteria.andEqualTo("county", countyName);
			}			
			resultList=userExtendsMapper.selectByExample(example);
		}
		
		return resultList;
	}


	@Override
	public List<Map<String, Object>> getUsersByAgentId(long agentId) {	
		UserExtends agent=userExtendsMapper.selectByPrimaryKey(agentId);
		//根据主帐号进行查询
		if(agent.getUserId()!=null && agent.getUserId()!=0){
			return userService.getUsersByParentId(agent.getUserId());
		}
		return new ArrayList<Map<String,Object>>();
	}
	
	
	

	

}
