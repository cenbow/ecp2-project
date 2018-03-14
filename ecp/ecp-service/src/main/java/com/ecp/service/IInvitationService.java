package com.ecp.service;

import java.util.List;

import com.ecp.entity.User;

public interface IInvitationService extends IBaseService<User, Long> {
	
	public List<User> selectByLoginid(Long loginId);
}
