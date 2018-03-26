package com.ecp.service.impl.back;

import org.springframework.stereotype.Service;

import com.ecp.dao.UserExtendsMapper;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IUserExtendsService;
import com.ecp.service.impl.AbstractBaseService;

@Service("userExtendsServiceBean")
public class UserExtendsServiceImpl extends AbstractBaseService<UserExtends, Long> implements IUserExtendsService {

	private UserExtendsMapper userExtendsMapper;

	/**
	 * @param userExtendsMapper the userExtendsMapper to set
	 * set方式注入
	 */
	public void setUserExtendsMapper(UserExtendsMapper userExtendsMapper) {
		this.userExtendsMapper = userExtendsMapper;
		this.setMapper(userExtendsMapper);
	}
	
}
