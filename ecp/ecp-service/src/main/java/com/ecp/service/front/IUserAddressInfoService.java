package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.User;
import com.ecp.entity.UserAddressInfo;
import com.ecp.service.IBaseService;


/**
 * @ClassName IUserAddressInfoService
 * @Description 用户地址信息服务
 * @author Administrator
 * @Date 2017年6月23日 下午12:47:14
 * @version 1.0.0
 */
public interface IUserAddressInfoService extends IBaseService<UserAddressInfo, Long> {
	/**
	 * @Description 藜取指定用户地址列表
	 * @param userId
	 * @return
	 */
	public List<UserAddressInfo> selectByBuyerId(long buyerId);	
	
	/**
	 * @Description 获取指定用户默认地址
	 * @param buyerId
	 * @return 如果有默认地址，则返回，否则返回null;
	 */
	public UserAddressInfo selectBuyerDefaultAddress(long buyerId);
}
