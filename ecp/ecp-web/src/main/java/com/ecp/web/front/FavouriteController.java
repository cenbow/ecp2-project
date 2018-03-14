package com.ecp.web.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ecp.bean.FavouriteBean;
import com.ecp.bean.FavouriteStatisticBean;
import com.ecp.common.SessionConstants;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.User;
import com.ecp.service.front.IFavouriteService;

/**
 * @ClassName FavouriteController
 * @Description 用户关注-控制器
 * @author Administrator
 * @Date 2017年5月5日 下午4:34:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/favourite")
public class FavouriteController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	@Autowired
	IFavouriteService favouriteService;
	
	

	/**
	 * @Description 商品加入购物车
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addToFavourite(long favouriteId, Model model, HttpServletRequest request) {

		// System.out.println("favouriteId is :"+favouriteId);

		// 如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		// TODO 判定用户是否已经登录
		if (user == null) { // 如果用户没有登录
			return RESPONSE_THYMELEAF + "login";
		} else { // 已经登录，将SPU加入我的收藏
			favouriteService.addToFavourite(favouriteId, user.getId());
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "added");
		jsonObject.put("status", "success");

		return jsonObject;
	}
	
	@RequestMapping(value = "/show")
	public String favourite_show(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		List<FavouriteBean> favouriteItems = favouriteService.getFavouritesByUserId(user.getId());
		List<FavouriteStatisticBean> statisticInfos = favouriteService.getFavouriteStatistic(user.getId());

		model.addAttribute("statisticInfos", statisticInfos);
		model.addAttribute("favouriteItems", favouriteItems);
		
		return RESPONSE_THYMELEAF + "favourite_table"; 
	}
	
	/**
	 * @Description 取消关注
	 * @param favouriteIds  关注条目id列表
	 * @param model
	 * @param request
	 * @return  状态
	 */
	@RequestMapping(value = "/cancelfavouritebatch")
	@ResponseBody
	public Object favourite_cancel(@RequestBody ArrayList<Long> favouriteIds,Model model, HttpServletRequest request) {
		
		for(Long favouriteId:favouriteIds){
			favouriteService.deleteByPrimaryKey(favouriteId);
		}
			
		return RequestResultUtil.getResultDeleteSuccess();
	}
	
	
	

}
