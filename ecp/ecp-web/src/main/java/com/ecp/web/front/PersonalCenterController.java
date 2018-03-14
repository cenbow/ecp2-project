package com.ecp.web.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecp.common.SessionConstants;
import com.ecp.entity.User;

/**
 * @ClassName PersonalCenterController
 * @Description 个人中心控制
 * @author Administrator
 * @Date 2017年5月8日 下午7:46:17
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/personalcenter")
public class PersonalCenterController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	/*@Autowired
	ICartService cartService;
	@Autowired
	IFavouriteService favouriteService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IOrderItemService orderItemService;*/

	/**
	 * @Description 个人中心-我的订单
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String orders(Model model, HttpServletRequest request) {
		return RESPONSE_THYMELEAF + "my_orders";
	}

	/**
	 * 
	 * @Description 个人中心-我的收藏
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/favourites", method = RequestMethod.GET)
	public String favourites(Model model, HttpServletRequest request) {
		return RESPONSE_THYMELEAF + "my_favourites";
	}

	/*@Autowired
	IItemService itemService;
	@Autowired
	ISkuService skuService;
	@Autowired
	ISkuPictureService skuPictureService;
	@Autowired
	IAttrValueService attrValueService;
	@Autowired
	IAttributeService attriubteService;*/

	/**
	 * 
	 * @Description 导航至-个人中心：我的购物车
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cart")
	public String cart(Integer cartItemId,Model model, HttpServletRequest request) {

		// 自session读取用户信息
		// 如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();

		model.addAttribute("cartItemId", cartItemId);  //需要自动选定的cart item

		return RESPONSE_THYMELEAF + "my_cart";
	}

}
