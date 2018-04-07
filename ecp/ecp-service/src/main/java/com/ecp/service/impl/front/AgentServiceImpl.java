package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.UserStatusType;
import com.ecp.dao.UserMapper;
import com.ecp.entity.User;
import com.ecp.service.front.IAgentService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service
public class AgentServiceImpl extends AbstractBaseService<User, Long> implements IAgentService {

	//private UserMapper userMapper;  //已经自抽象类继承了变更此处不必再声明一个变量
	
	UserMapper userMapper;

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper=userMapper;
		this.setMapper(userMapper);
	}

	/** 
	 * @author: srd
	 * @see com.donglicms.service.IUserService#login(java.lang.String, java.lang.String)
	 * 登录验证
	 */	
	@Override
	public User login(String loginName, String password) {
		User user = new User();
		user.setUsername(loginName);
		user.setPassword(password);
		user.setStatus(UserStatusType.VALID_USER);
		List<User> userList = userMapper.select(user);
		
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}	

	@Override
	public List<User> getByLoginName(String loginName) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("loginName", loginName);
		List<User> list=userMapper.selectByExample(example);
		return list;
	}

	@Override
	public int register(String loginName, String password) {
		User regUser=new User();
		regUser.setUsername(loginName);
		regUser.setPassword(password);
		userMapper.insert(regUser);
		return 0;
	}

	@Override
	public boolean hasSameLoginName(String loginName) {
		User record=new User();
		record.setUsername(loginName);
		
		List<User> list=userMapper.select(record);
		if (list.size()>0) 
			return true;
		
		return false;
	}

	@Override
	public List<Map<String, Object>> getUsersByParentId(long pid) {
		return userMapper.getUsersByParentId(pid);
	}

	

}
