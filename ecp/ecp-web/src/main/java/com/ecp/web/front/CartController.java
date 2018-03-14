package com.ecp.web.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.bean.AddSkuToOrderBean;
import com.ecp.bean.CartItemBean;
import com.ecp.bean.CartToOrderItemList;
import com.ecp.bean.DeletedType;
import com.ecp.bean.FollowCartItemBean;
import com.ecp.bean.ItemStatusType;
import com.ecp.bean.SkuPriceBean;
import com.ecp.bean.SkuType;
import com.ecp.common.SessionConstants;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Attribute;
import com.ecp.entity.AttributeValue;
import com.ecp.entity.Favourite;
import com.ecp.entity.Item;
import com.ecp.entity.Sku;
import com.ecp.entity.SkuPicture;
import com.ecp.entity.User;
import com.ecp.entity.UserAddressInfo;
import com.ecp.service.front.IAttrValueService;
import com.ecp.service.front.IAttributeService;
import com.ecp.service.front.ICartService;
import com.ecp.service.front.IFavouriteService;
import com.ecp.service.front.IItemService;
import com.ecp.service.front.ISkuPictureService;
import com.ecp.service.front.ISkuService;
import com.ecp.service.front.IUserAddressInfoService;

/**
 * @ClassName CartController
 * @Description 购物车-控制器
 * @author Administrator
 * @Date 2017年5月5日 下午4:34:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/cart")
public class CartController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	@Autowired
	ICartService cartService;
	@Autowired
	IUserAddressInfoService userAddressInfoService;
	@Autowired
	IItemService itemService;
	@Autowired
	ISkuService skuService;
	@Autowired
	ISkuPictureService skuPictureService;
	@Autowired
	IAttrValueService attrValueService;
	@Autowired
	IAttributeService attriubteService;
	@Autowired
	IFavouriteService favouriteService;
	
	
	
	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param cartItemId  用户选定的购物车条目（用于加入购物车时-->结算）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String cart_show(Integer cartItemId,Model model, HttpServletRequest request) {
		// 自session读取用户信息
		// 如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();

		// 准备数据
		// (1)准备购物车条目数据
		List<CartItemBean> cartItemList = new ArrayList<CartItemBean>();  //向前台传递的购物车条目 
		List<Favourite> tempCartItems = cartService.getCartItemByUserId(userId, (byte) 1);  //自购物车中读取条目
		List<Favourite> cartItems=new ArrayList<Favourite>();
		for(Favourite fav:tempCartItems){
			Sku sku=skuService.selectByPrimaryKey((long)fav.getSkuId());
			if(sku.getDeleted()==DeletedType.NO){
				cartItems.add(fav);
			}
		}		
		
		for (Favourite cartItem : cartItems) {
			Item item = itemService.getItemById((long) cartItem.getItemId());
			if(item==null || item.getDeleted()==DeletedType.YES || item.getItemStatus()!=ItemStatusType.IS_SALING) continue;
			
			
			CartItemBean itemBean = new CartItemBean();
			cartItemList.add(itemBean); // add into cartItemList

			// userid quantity skuId itemId
			itemBean.setId(cartItem.getId());
			itemBean.setUserId(userId);			
			itemBean.setQuantity(cartItem.getQuantity());
			itemBean.setSkuId(cartItem.getSkuId());
			itemBean.setItemId(cartItem.getItemId());

			
			itemBean.setItemName(item.getItemName());
			itemBean.setWeightUnit(item.getWeightUnit());
			itemBean.setCid(item.getCid());

			// get sku price and weight
			SkuPriceBean skuPriceBean = skuService.getSkuBySkuId(cartItem.getSkuId());
			itemBean.setSkuPrice(skuPriceBean.getSell_price());
			itemBean.setSkuWeight(skuPriceBean.getWeight());

			// get sku attr value names
			// 将sku attribute 分隔
			
			String skuName = item.getItemName();
			if(StringUtils.isNotBlank(skuPriceBean.getAttributes())){
				String skuAttrArray[] = skuPriceBean.getAttributes().split(",");
				for (String attrValuePair : skuAttrArray) {
					String skuValueName = "";

					String[] avPair = attrValuePair.split(":");
					long attrId = Long.parseLong(avPair[0]);
					long valueId = Long.parseLong(avPair[1]);

					AttributeValue attributeValue = attrValueService.getAttributeValueById(attrId, valueId);
					Attribute attr = attriubteService.getAttributeById(attrId);

					skuValueName = attr.getAttrName() + ":" + attributeValue.getValueName();
					itemBean.getSkuAttrValueNames().add(skuValueName);

					skuName = skuName + skuValueName; // 生成sku name 生成sku
				}
			}
			
			itemBean.setSkuName(skuName);

			// get sku picture
			List<SkuPicture> skuPicts = skuPictureService.getSkuPictureById((long) cartItem.getSkuId());
			itemBean.setSkuPicture(skuPicts.get(0).getPictureUrl());

		}
		model.addAttribute("cartItemList", cartItemList); 	// 加入到model
		
		//需要自动选定的cart item
		model.addAttribute("cartItemId", cartItemId);  		
		
		//(2)商品数量
		int itemNum = cartItemList.size();
		model.addAttribute("itemNum", itemNum);

		return RESPONSE_THYMELEAF + "my_cart_table";
	}
	
	/**
	 * @Description 将单个关注条目加入购物车
	 * @param itemId  spuId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/favouritetocart", method = RequestMethod.POST)
	@ResponseBody
	public Object favourite_to_cart(Integer itemId,Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		favouriteToCart(itemId,userId);
		
		return RequestResultUtil.getResultAddSuccess();
	}
	
	
	@RequestMapping(value = "/favouritetocartbatch", method = RequestMethod.POST)
	@ResponseBody
	public Object favourite_to_cart_batch(@RequestBody ArrayList<Integer> itemIds,Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		for(Integer itemId:itemIds){
			favouriteToCart(itemId,userId);
		}
		
		return RequestResultUtil.getResultAddSuccess();
	}
	
	/**
	 * @Description 将spuId加入购物车（将主SKU加入购物车）
	 * @param itemId  spuId
	 */
	private void favouriteToCart(Integer itemId,Long userId){
		List<SkuPriceBean> skuList = skuService.getSkuByIdAndType((long)itemId, SkuType.PRIMARY);
		int skuId=Integer.parseInt(Long.toString(skuList.get(0).getSku_id())); // 由于返回的是list类型
		cartService.addToCart(itemId, skuId, (int)1, Integer.parseInt(userId.toString()));
	}
	
	
	/**
	 * @Description 将购车单个条目加入关注
	 * @param spuId spuId
	 * @param model
	 * @return  是否加入成功的MAP对象
	 */
	@RequestMapping(value = "/addtofavourite", method = RequestMethod.POST)
	@ResponseBody
	public Object add_into_user_favourite(long spuId,Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		int row=favouriteService.addToFavourite(spuId, userId);  //加入我的关注
		return RequestResultUtil.getResultAddSuccess();
		/*return RequestResultUtil.getResultAddWarn();*/
	}
	
	/**
	 * @Description 将购车单个条目    移入到关注
	 * @param cartItemId 购物车条目id
	 * @param spuId  spu id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removetofavourite", method = RequestMethod.POST)
	@ResponseBody
	public Object remove_into_user_favourite(Integer cartItemId,long spuId,Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		cartService.deleteByPrimaryKey(cartItemId);  //删除购物车中的条目
		int row=favouriteService.addToFavourite(spuId, userId);  //加入到我的关注
		
		return RequestResultUtil.getResultAddSuccess();
	}
	
	/**
	 * @Description 批量删除购物车条目
	 * @param cartItemIds
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removebatch", method = RequestMethod.POST)
	@ResponseBody
	public Object remove_batch(@RequestBody ArrayList<Integer> cartItemIds,Model model, HttpServletRequest request){
		
		for(int cartItemid:cartItemIds){
			cartService.deleteByPrimaryKey(cartItemid);
		}
		return RequestResultUtil.getResultDeleteSuccess();
	}
	
	/**
	 * @Description 批量移除到我的关注
	 * @param cartItems
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/followbatch", method = RequestMethod.POST)
	@ResponseBody
	public Object follow_batch(@RequestBody ArrayList<FollowCartItemBean> cartItems,Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		
		for(FollowCartItemBean item:cartItems){
			favouriteService.addToFavourite(item.getItemId(), userId);  //加入到我的关注
			cartService.deleteByPrimaryKey(item.getId());  //移除购物车条目
			
		}
		return RequestResultUtil.getResultDeleteSuccess();
	}
	
	/**
	 * @Description 更新购物车条目商品数量
	 * @param cartItemId
	 * @param quantity
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatequantity", method = RequestMethod.POST)
	@ResponseBody
	public Object cart_update_quantity(Integer cartItemId,Integer quantity, Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();
		
		Favourite entity=new Favourite();
		entity.setId(cartItemId);
		entity.setQuantity(quantity);
		cartService.updateByPrimaryKeySelective(entity); //更新商品数量
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	
	/**
	 * @Description sku加购物车
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addSkuToCart(int itemId, int skuId, int quantity, String skuName, Model model,
			HttpServletRequest request) {
		
		Integer cartItemId=null;

		//如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);

		//TODO 判定用户是否已经登录
		if (user == null) { //如果用户没有登录
			return RESPONSE_THYMELEAF + "login";
		} else { //已经登录，将sku加入到购物车中
			cartItemId=cartService.addToCart(itemId, skuId, quantity, Integer.parseInt(user.getId().toString()));
		}

		//此处只取了第一个图片  TODO
		List<SkuPicture> skuPictList=skuPictureService.getSkuPictureById((long)skuId);
		if(skuPictList.size()>0){
			model.addAttribute("skuPict", skuPictList.get(0).getPictureUrl());
		}
		else{
			model.addAttribute("skuPict", null);
		}
		
		model.addAttribute("skuName", skuName);   //sku 名称
		model.addAttribute("quantity", quantity);  //数量
		model.addAttribute("itemId", itemId);  //spu id
		model.addAttribute("skuId", skuId);  //sku id
		model.addAttribute("cartItemId", cartItemId);  //cart item id

		return RESPONSE_THYMELEAF + "my_cart_add_ok";
	}
	
	/**
	 * @Description 删除购物车条目
	 * @param id  条目id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object cartItem_delete(Integer id,HttpServletRequest request){
		int row = cartService.deleteByPrimaryKey(id);
		return RequestResultUtil.getResultDeleteSuccess();
		/*if (row > 0) {
			return RequestResultUtil.getResultDeleteSuccess();
		}

		return RequestResultUtil.getResultDeleteWarn();*/
	}
	
	
	/**
	 * 
	 * @Description 加载：我的购物车-quickcart
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/quickcart")
	public String quick_cart(Model model, HttpServletRequest request) {

		// 自session读取用户信息
		// 如果用户在此处没有登录时
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		long userId = user.getId();

		// 准备数据
		// (1)准备购物车条目数据		
		List<CartItemBean> cartItemList = new ArrayList<CartItemBean>();  //向前台传递的购物车条目 
		List<Favourite> tempCartItems = cartService.getCartItemByUserId(userId, (byte) 1);  //自购物车中读取条目
		List<Favourite> cartItems=new ArrayList<Favourite>();
		for(Favourite fav:tempCartItems){  //过滤掉已经删除的SKU
			Sku sku=skuService.selectByPrimaryKey((long)fav.getSkuId());
			if(sku.getDeleted()==DeletedType.NO){
				cartItems.add(fav);
			}
		}
		
		for (Favourite cartItem : cartItems) {  
			Item item = itemService.getItemById((long) cartItem.getItemId());  //获取购物车条目商品信息
			if(item==null || item.getDeleted()==DeletedType.YES || item.getItemStatus()!=ItemStatusType.IS_SALING) continue;
			
			CartItemBean itemBean = new CartItemBean();
			cartItemList.add(itemBean); // add into cartItemList

			// userid quantity skuId itemId
			itemBean.setId(cartItem.getId());
			itemBean.setUserId(userId);			
			itemBean.setQuantity(cartItem.getQuantity());
			itemBean.setSkuId(cartItem.getSkuId());
			itemBean.setItemId(cartItem.getItemId());

		
			itemBean.setItemName(item.getItemName());
			itemBean.setWeightUnit(item.getWeightUnit());
			itemBean.setCid(item.getCid());

			// get sku price and weight
			SkuPriceBean skuPriceBean = skuService.getSkuBySkuId(cartItem.getSkuId());
			if(skuPriceBean==null) continue;
			itemBean.setSkuPrice(skuPriceBean.getSell_price());
			itemBean.setSkuWeight(skuPriceBean.getWeight());

			// get sku attr value names
			// 将sku attribute 分隔   //TODO 程序代码合并优化
			String skuName = item.getItemName();
			if(StringUtils.isNotBlank(skuPriceBean.getAttributes())){
				String skuAttrArray[] = skuPriceBean.getAttributes().split(",");
				for (String attrValuePair : skuAttrArray) {
					String skuValueName = "";

					String[] avPair = attrValuePair.split(":");
					long attrId = Long.parseLong(avPair[0]);
					long valueId = Long.parseLong(avPair[1]);

					AttributeValue attributeValue = attrValueService.getAttributeValueById(attrId, valueId);
					Attribute attr = attriubteService.getAttributeById(attrId);

					skuValueName = attr.getAttrName() + ":" + attributeValue.getValueName();
					itemBean.getSkuAttrValueNames().add(skuValueName);

					skuName = skuName + skuValueName; // 生成sku name 生成sku
				}
			}			
			itemBean.setSkuName(skuName);

			// get sku picture
			List<SkuPicture> skuPicts = skuPictureService.getSkuPictureById((long) cartItem.getSkuId());
			itemBean.setSkuPicture(skuPicts.get(0).getPictureUrl());
		}
		model.addAttribute("cartItemList", cartItemList); // 加入到model
		
		//(2)计算总金额
		BigDecimal cartItemTotalPrice=calcCartItemTotalPrice(cartItemList);
		model.addAttribute("cartItemTotalPrice", cartItemTotalPrice); // 加入到model
		
		//(3)商品数量
		int itemNum = cartItemList.size();
		model.addAttribute("itemNum", itemNum);
		

		return RESPONSE_THYMELEAF + "quickCart";
	}
	

	/**
	 * @Description 将购物车所选数据置于session中  此种方式并不好，采用动态表单来处理。
	 * @param model
	 * @param request
	 * @return 结算页面（settle_account）
	 */
	@RequestMapping("/gosettle")
	@ResponseBody
	public Object cart_go_settlement(@RequestBody List<AddSkuToOrderBean> cartItemList, Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.setAttribute("cartItemList", cartItemList);

		Map<String, String> respMap = new HashMap<String, String>();
		respMap.clear();
		respMap.put(RequestResultUtil.RESULT_CODE, RequestResultUtil.RESULT_CODE_SUCCESS);
		respMap.put(RequestResultUtil.RESULT_MSG, "/front/cart/settle");  //导航到结算页面
		return respMap;
	}

	/**
	 * @Description 结算页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/settle")
	public String cart_settlement(CartToOrderItemList cartToOrderItemList,Model model, HttpServletRequest request) {
		
		List<AddSkuToOrderBean> cartItemList=cartToOrderItemList.getCartItemList();
		//传递自购物车中所选择的SKU列表
		model.addAttribute("cartItemList", cartItemList);

		//获取买家地址
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(SessionConstants.USER);
		List<UserAddressInfo> addrs=userAddressInfoService.selectByBuyerId(user.getId());
		model.addAttribute("addrs", addrs);
		
		//计算商品数量及总金额
		int cartItemNumAmount=calcCartItemAmount(cartItemList);
		BigDecimal totalPayable=calcCartItemTotalPayable(cartItemList);
		//加入model
		model.addAttribute("cartItemNumAmount", cartItemNumAmount);  //商品数量
		model.addAttribute("totalPayable", totalPayable);  //总金额

		return RESPONSE_THYMELEAF+"settle_account";
	}
	
	
	private int calcCartItemAmount(List<AddSkuToOrderBean> cartItemList){
		int amount=0;
		for(AddSkuToOrderBean cartItem:cartItemList){
			amount=amount+cartItem.getSkuNum();
		}
		return amount;
	}
	
	/**
	 * @Description 计算应付总金额（优惠前）
	 * @param cartItemList  己选购物车中商品列表
	 * @return
	 */
	private BigDecimal calcCartItemTotalPayable(List<AddSkuToOrderBean> cartItemList){
		BigDecimal total=new BigDecimal(0.00);
		for(AddSkuToOrderBean cartItem:cartItemList){
			total=total.add(cartItem.getSkuPrice().multiply(new BigDecimal(cartItem.getSkuNum())));
		}
		return total;
	}
	
	
	/**
	 * @Description 计算购物车总金额
	 * @param cartItemList 购物车行项目列表
	 * @return
	 */
	private BigDecimal calcCartItemTotalPrice(List<CartItemBean> cartItemList){
		BigDecimal total=new BigDecimal(0.00);
		for(CartItemBean cartItem:cartItemList){
			total=total.add(cartItem.getSkuPrice().multiply(new BigDecimal(cartItem.getQuantity())));
		}
		return total;
	}

}
