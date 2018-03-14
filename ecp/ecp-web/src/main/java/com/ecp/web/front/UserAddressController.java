package com.ecp.web.front;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ecp.bean.UserAddressType;
import com.ecp.common.SessionConstants;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.User;
import com.ecp.entity.UserAddressInfo;
import com.ecp.service.front.IUserAddressInfoService;

/**
 * @ClassName UserAddressController
 * @Description 用户地址前端控制器
 * @author Administrator
 * @Date 2017年6月23日 下午12:41:21
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/useraddress")
public class UserAddressController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	@Autowired
	IUserAddressInfoService userAddressInfoService;

	/**
	 * @Description 增加用户收货地址 AJAX请求
	 * @param userAddressInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Object user_address_insert(UserAddressInfo userAddressInfo, HttpServletRequest request) {

		//获取登录用户信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		//设置用户地址相关值
		userAddressInfo.setCreateTime(new Date());
		userAddressInfo.setBuyerId(user.getId());
		userAddressInfo.setIsDefault(UserAddressType.NOT_DEFAULT);  //新加入的地址非默认地址
		int row = userAddressInfoService.insertSelective(userAddressInfo);
		if (row > 0) {
			return RequestResultUtil.getResultAddSuccess();
		}

		return RequestResultUtil.getResultAddWarn();
	}
	
	
	/**
	 * @Description 修改用户收货地址 AJAX请求
	 * @param userAddressInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object user_address_updatet(UserAddressInfo userAddressInfo, HttpServletRequest request) {

		//设置用户地址相关值
		userAddressInfo.setUpdteTime(new Date());  //更新时间
		int row = userAddressInfoService.updateByPrimaryKeySelective(userAddressInfo);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}

		return RequestResultUtil.getResultUpdateWarn();
	}
	
	
	
	/**
	 * @Description 删除-用户收货地址 AJAX请求
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object user_address_delete(long id, HttpServletRequest request) {

		
		int row = userAddressInfoService.deleteByPrimaryKey(id);
		if (row > 0) {
			return RequestResultUtil.getResultDeleteSuccess();
		}

		return RequestResultUtil.getResultDeleteWarn();
	}
	
	
	
	
	/**
	 * @Description 删除-用户收货地址 AJAX请求
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setdefault", method = RequestMethod.POST)
	@ResponseBody
	public Object user_address_set_default(long id, HttpServletRequest request) {
		
		//TODO 需要加入事务处理 后面加入transaction
		//(1)读取原来的默认地址，修改为非默认地址
		//获取登录用户信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		UserAddressInfo userAddress=userAddressInfoService.selectBuyerDefaultAddress(user.getId());
		if(userAddress!=null){
			userAddress.setIsDefault(UserAddressType.NOT_DEFAULT);
			userAddressInfoService.updateByPrimaryKeySelective(userAddress);
		}
		
		//(2)根据所传入的地址id,修改为默认地址
		UserAddressInfo newDefault=new UserAddressInfo();
		newDefault.setId(id);
		newDefault.setIsDefault(UserAddressType.DEFAULT);
		int row=userAddressInfoService.updateByPrimaryKeySelective(newDefault);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}

		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * @Description 获取需要编辑的地址对象  (AJAX请求)
	 * @param id  地址id
	 * @return  
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object user_address_get(long id) {
		//(1)取得指定地址，并转换成JSON格式
		UserAddressInfo addr=userAddressInfoService.selectByPrimaryKey(id);
		String addrJSONStr=JSON.toJSONString(addr);  
		
		//(2)返回所请求的对象（JSON格式）
		Map<String,String> result=new HashMap<String,String>();
		result.put(RequestResultUtil.RESULT_CODE, RequestResultUtil.RESULT_CODE_SUCCESS);
		result.put(RequestResultUtil.RESULT_MSG, addrJSONStr);
		
		return result;
	}
	

	/**
	 * @Description 显示用户地址列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String user_address_show(HttpServletRequest request, Model model) {
		//如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		//获取买家用户地址
		List<UserAddressInfo> addrs = userAddressInfoService.selectByBuyerId(user.getId());
		model.addAttribute("addrs", addrs);
		
		return RESPONSE_THYMELEAF+"user_address_info_table";
	}

}
