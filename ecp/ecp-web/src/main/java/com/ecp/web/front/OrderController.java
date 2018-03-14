package com.ecp.web.front;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.ecp.bean.AddSkuToOrderBean;
import com.ecp.bean.CancelType;
import com.ecp.bean.CartToOrderItemList;
import com.ecp.bean.DeletedType;
import com.ecp.common.SessionConstants;
import com.ecp.common.util.OrderIdGenerator;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Orders;
import com.ecp.entity.User;
import com.ecp.entity.UserAddressInfo;
import com.ecp.entity.UserExtends;
import com.ecp.service.front.ICartService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAddressInfoService;
import com.ecp.service.front.IUserAgentService;

/**
 * @ClassName FavouriteController
 * @Description 用户订单-控制器
 * @author Administrator
 * @Date 2017年5月5日 下午4:34:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/order")
public class OrderController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	/*
	 * @Autowired IdGenerator orderIdGenerator;
	 */

	@Autowired
	IOrderService orderService;
	@Autowired
	IOrderItemService orderItemService;
	@Autowired
	ICartService cartService;
	@Autowired
	IUserAddressInfoService userAddressInfoService;
	@Autowired
	IUserAgentService userAgentService;
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String order_create(CartToOrderItemList cartToOrderItemList, Model model, HttpServletRequest request) {

		//获取所选购物车中的商品列表
		List<AddSkuToOrderBean> cartItemList = cartToOrderItemList.getCartItemList();
		UserAddressInfo addr = userAddressInfoService.selectByPrimaryKey(cartToOrderItemList.getAddrId());

		// 如果用户在此处没有登录时
		/*HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);*/

		String orderId = createOrder(cartItemList, addr,cartToOrderItemList.getMemo()); //增加订单
		orderItemService.addItemIntoOrder(cartItemList, orderId); //增加订单条目
		delSelectedCartItem(cartItemList); //自购购物车删除用户已下单商品

		//准备用于订单提交成功的数据
		model.addAttribute("orderNo", orderId); //订单号
		BigDecimal totalPrice = calcCartItemTotalPayable(cartItemList); //calc total_price  计算优惠前总金额
		model.addAttribute("totalPrice", totalPrice); //订单金额
		model.addAttribute("orderTime", new Date()); //订单时间

		//订单提交成功页面
		return RESPONSE_THYMELEAF + "my_order_add_ok";
	}

	@RequestMapping(value = "/show")
	public String order_show(int orderTimeCond,int dealStateCond, Model model, HttpServletRequest request) {
		/*System.out.println("order-time-cond:"+orderTimeCond);
		System.out.println("deal-state-cond:"+dealStateCond);*/
		
		//回传查询条件
		model.addAttribute("orderTimeCond", orderTimeCond);
		model.addAttribute("dealStateCond", dealStateCond);
		
		// (1）获取订单信息
		// (2)获取订单下条目信息

		//System.out.println("--------------debug start");
		// 用于页面显示
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();

		// 获取登录用户信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		// 查询当前登录用户的订单
		//List<Orders> orders = orderService.selectOrderByUserId(user.getId());
		List<Orders> orders = orderService.selectOrderByOrderTimeAndDealState(user.getId(), orderTimeCond, dealStateCond);
		for (Orders order : orders) { // 迭代订单，查询订单条目

			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("order", order);

			// 读取订单条目数据
			List<Map<String, String>> orderItems = orderItemService.selectItemsByOrderId(order.getOrderId());
			//System.out.println("查询到的条目有："+orderItems.size());

			orderMap.put("orderItems", orderItems);
			orderMap.put("orderItemNum", orderItems.size());  //订单中商品条数

			orderList.add(orderMap); //加入列表中

		}

		model.addAttribute("orderList", orderList);

		//System.out.println("--------------debug end");

		return RESPONSE_THYMELEAF + "my_order_table";
	}
	
	/**
	 * @Description 删除订单
	 * @param id  订单id（订单key primary key）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object order_delete(Long id,HttpServletRequest request){
		Orders entity=new Orders();
		entity.setId(id);
		entity.setDeleted(DeletedType.YES);
		entity.setDeleteTime(new Date());
		
		int row=orderService.updateByPrimaryKeySelective(entity);
		if (row > 0) {
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * @Description 取消订单
	 * @param id  订单id（订单key primary key）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancel")
	@ResponseBody
	public Object order_cancel(Long id,HttpServletRequest request){
		Orders entity=new Orders();
		entity.setId(id);
		
		entity.setYn(CancelType.YES);  //取消订单
		entity.setCancelTime(new Date());  //订单取消时间
		
		
		int row=orderService.updateByPrimaryKeySelective(entity);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * @Description 恢复购买（与取消订单相对）
	 * @param id  订单id（订单key primary key）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/buyagain")
	@ResponseBody
	public Object order_buy_again(Long id,HttpServletRequest request){
		Orders entity=new Orders();
		entity.setId(id);
		
		entity.setYn(CancelType.NO);  //恢复购买（取消订单的逆操作）
		entity.setUpdateTime(new Date());  //更新日期
		
		int row=orderService.updateByPrimaryKeySelective(entity);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	@RequestMapping(value = "/detail")
	public String order_detail(Long id,Model model,HttpServletRequest request){
		
		model.addAttribute("orderId", id);  //向订单详细table传递参数
		
		return RESPONSE_THYMELEAF + "order_detail";
	}
	
	/**
	 * @Description 订单详情模块页
	 * @param id  订单id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detailtable")
	public String order_detail_table(Long id,Model model,HttpServletRequest request){
		
		//(1)读取订单
		Orders order=orderService.selectByPrimaryKey(id);		
		//(2)读取订单商品列表
		List<Map<String, String>> orderItems = orderItemService.selectItemsByOrderId(order.getOrderId());
		//(3)收货人信息(此信息已经保存至订单中)		
		//(4)代理商信息
		//UserExtends agent=userAgentService.selectByPrimaryKey(order.getBuyerId());
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("agent", agent);
		
		return RESPONSE_THYMELEAF + "order_detail_table";
	}
	
	

	/**
	 * @Description 计算应付总金额（优惠前）
	 * @param cartItemList
	 *            己选购物车中商品列表
	 * @return
	 */
	private BigDecimal calcCartItemTotalPayable(List<AddSkuToOrderBean> cartItemList) {
		BigDecimal total = new BigDecimal(0.00);
		for (AddSkuToOrderBean cartItem : cartItemList) {
			total = total.add(cartItem.getSkuPrice().multiply(new BigDecimal(cartItem.getSkuNum())));
		}
		return total;
	}

	/**
	 * @Description 增加订单
	 * @param cartItemList
	 *            用户所选商品列表
	 * @param addr
	 *            收货地址
	 * @param memo
	 * 			 订单备注
	 * @return 订单号
	 */
	private String createOrder(List<AddSkuToOrderBean> cartItemList, UserAddressInfo addr,String memo) {
		String orderId = OrderIdGenerator.getOrderIdByUUId(); //生成订单号
		Orders order = new Orders();

		order.setOrderId(orderId); //订单号
		order.setCreateTime(new Date()); //订单创建时间
		order.setOrderTime(new Date()); //下单时间
		order.setBuyerId(addr.getBuyerId()); //买家id
		order.setName(addr.getContactPerson()); //收货人姓名
		order.setPhone(addr.getContactTel()); //收货人固定电话
		order.setMobile(addr.getContactPhone()); //收货人手机号码
		order.setEmail(addr.getContactEmail()); //收货人邮件
		order.setFullAddress(addr.getFullAddress()); //收货全地址
		order.setMemo(memo);
		BigDecimal totalPrice = calcCartItemTotalPayable(cartItemList); //calc total_price  计算优惠前总金额
		order.setTotalPrice(totalPrice); //优惠前总金额

		//TODO set other field's value

		orderService.insertSelective(order);

		return orderId;
	}

	/**
	 * @Description 删除购物车中已经加入订单中的商品
	 * @param cartItemList
	 */
	private void delSelectedCartItem(List<AddSkuToOrderBean> cartItemList) {
		for (AddSkuToOrderBean item : cartItemList) {
			String idStr = Long.toString(item.getId());
			cartService.deleteByPrimaryKey(Integer.parseInt(idStr));
		}

	}

}
