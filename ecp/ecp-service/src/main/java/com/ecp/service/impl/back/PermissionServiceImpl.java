package com.ecp.service.impl.back;

import org.springframework.stereotype.Service;

import com.ecp.dao.PermissionMapper;
import com.ecp.entity.Permission;
import com.ecp.service.back.IPermissionService;
import com.ecp.service.impl.AbstractBaseService;

@Service("permissionServiceBean")
public class PermissionServiceImpl extends AbstractBaseService<Permission, Long> implements IPermissionService {

	private PermissionMapper permisstionMapper;

	/**
	 * @param permisstionMapper the permisstionMapper to set
	 * set方式注入
	 */
	public void setPermissionMapper(PermissionMapper permisstionMapper) {
		this.permisstionMapper = permisstionMapper;
		this.setMapper(permisstionMapper);
	}

}
