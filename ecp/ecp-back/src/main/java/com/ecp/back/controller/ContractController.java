package com.ecp.back.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ecp.bean.AccountItemType;
import com.ecp.bean.ContractAttrValueBean;
import com.ecp.bean.ContractOrderItemBean;
import com.ecp.bean.ContractOrderItemDisplayBean;
import com.ecp.bean.ContractStateType;
import com.ecp.bean.UserBean;
import com.ecp.common.util.EntityToMap;
import com.ecp.common.util.NumberToCN;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
import com.ecp.entity.Brand;
import com.ecp.entity.CompanyInfo;
import com.ecp.entity.Contract;
import com.ecp.entity.ContractAttrValue;
import com.ecp.entity.ContractAttribute;
import com.ecp.entity.ContractItems;
import com.ecp.entity.CustLockRel;
import com.ecp.entity.Item;
import com.ecp.entity.Orders;
import com.ecp.entity.Sku;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IAttributeValueService;
import com.ecp.service.back.ICompanyInfoService;
import com.ecp.service.back.IUserExtendsService;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IAttributeService;
import com.ecp.service.front.IBrandService;
import com.ecp.service.front.IContractAttrValueService;
import com.ecp.service.front.IContractAttributeService;
import com.ecp.service.front.IContractItemsService;
import com.ecp.service.front.IContractService;
import com.ecp.service.front.IItemService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.ISkuService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.StringUtil;



/**
 * @ClassName ContractController
 * @Description 合同管理
 * @author Administrator
 * @Date 2017年6月22日 上午11:52:24
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/contract")
public class ContractController {
	final String RESPONSE_THYMELEAF_BACK_ORDER="back/thymeleaf/order/";
	final String RESPONSE_THYMELEAF_BACK_CONTRACT="back/thymeleaf/contract/";
	final String RESPONSE_JSP="jsps/front/";
	
	private final int PAGE_SIZE = 8;

	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	IOrderItemService orderItemService;	
	@Autowired
	IContractAttributeService contractAttributeService;
	@Autowired
	IContractService contractService;
	@Autowired
	IContractItemsService contractItemsService;
	@Autowired
	IContractAttrValueService contractAttrValueService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IUserAgentService userAgentService;
	@Autowired
	ICompanyInfoService companyInfoService;
	@Autowired
	IBrandService brandService;
	@Autowired
	IItemService itemService;
	@Autowired
	ISkuService skuService;
	@Autowired
	IAttributeService attributeService;
	@Autowired
	IAttributeValueService attributeValueService;	
	@Autowired
	IAccountCompanyService accountCompanyService;
	@Autowired
	IAccountPersonalService accountPersonalService;
	@Autowired
	IAgentBindService agentBindService;
	@Resource(name="userServiceBean")
	private IUserService userService;
	@Resource(name="userExtendsServiceBean")
	private IUserExtendsService userExtendsService;
	
	/**
	 * @Description 合同详情
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String contract_detail(long id,Model model){
		
		model.addAttribute("contractId", id);
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_detail";
	}
	
	
	/**
	 * @Description 合同详情模块
	 * @param id  合同id(pk)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/detailtable")
	public String contract_detail_table(long id,Model model){
		//(1)合同对象
		Contract contract=contractService.selectByPrimaryKey(id);		
		model.addAttribute("contract", contract);
		
		//(2) order:select order by orderNo
		Orders order=orderService.selectOrderByOrderNo(contract.getOrderNo());
		//model.addAttribute("order", order);
		
		//(3)合同商品条目(根据合同编号)
		List<Map<String,Object>> contractItems=contractItemsService.selectItemsByContractNo(contract.getContractNo());
		model.addAttribute("contractItems", contractItems);
		BigDecimal contractItemTotal=calcContractItemTotalPrice(contractItems);  //计算合同优惠后总额
		model.addAttribute("contractItemTotal", contractItemTotal);
		
		
		//(4)代理商
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		model.addAttribute("agent", agent);
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_detail_table";
	}
	
	
	
	
	/**
	 * @Description 显示-合同列表
	 * @param model
	 * @return  
	 */
	@RequestMapping(value = "/show")
	public String contract_show(Model model) {
		return RESPONSE_THYMELEAF_BACK_CONTRACT + "contract_show";
	}
	
	/**
	 * @Description 显示-合同列表
	 * @param orderTimeCond   时间 
	 * @param dealStateCond
	 * @param pageNum
	 * @param pageSize
	 * @param searchTypeValue
	 * @param condValue
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/contracttable")
	public String contract_table(int timeCond,int dealStateCond,Integer pageNum, Integer pageSize,Integer searchTypeValue,String condValue,Model model) {
		
		if(pageNum==null || pageNum==0)
		{
			pageNum=1;
			pageSize=PAGE_SIZE;
		}
		
		//置默认值(搜索)
		if(searchTypeValue==null){
			searchTypeValue=0;
			condValue="";
		}
		
		//回传查询条件
		model.addAttribute("orderTimeCond", timeCond);
		model.addAttribute("dealStateCond", dealStateCond);
		//搜索类型及搜索关键字
		model.addAttribute("searchTypeValue", searchTypeValue);  	//查询字段值
		model.addAttribute("condValue", condValue);  				//查询条件值
		
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper			

		//List<Contract> contractList = contractService.selectAll();
		List<Map<String,Object>> contractList=contractService.selectContract(timeCond,dealStateCond,searchTypeValue,condValue); 
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(contractList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		//setPageInfo(model, pageInfo); // 向前台传递分页信息
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("contractList", contractList);
		
		return RESPONSE_THYMELEAF_BACK_CONTRACT + "contract_table";
		
	}
	
	
	/**
	 * @Description 创建合同
	 * @param id  订单主键:id
	 * @param orderId 订单编号 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public String contract_add(long id,String orderId,Model model){
		//（1）获取关于订单中详细条目
		List<Map<String,String>> orderItemList = orderItemService.selectItemsByOrderId(orderId);
		model.addAttribute("orderItemList", orderItemList);
		
		//(2)获取合同属性
		//TODO 查询条件：template_id=1  此处暂时直接查询，而后进行修改		
		long templateId=1L;
		List<ContractAttribute> contractAttrList=contractAttributeService.selectAttributesByTemplateId(templateId);
		model.addAttribute("contractAttrList", contractAttrList);
		
		//(3)加入订单信息
		Orders order=orderService.selectByPrimaryKey(id);
		model.addAttribute("order", order);
		
		//(4)加入代理商信息
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		model.addAttribute("agent", agent);
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_add";
	}
	
	/**
	 * @Description 合同预览
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/preview")
	public String contract_preview(String contractAttrVals,String orderItems,Model model){
		
		//(1)合同属性
		List<ContractAttrValueBean> contractAttrValueList=getEntity1(contractAttrVals);
		Map<String,String> attrValueMap=  convertAttrValueList(contractAttrValueList);
		
		//(2)合同商品列表
		List<ContractOrderItemBean> contractOrderItemBeanList=getEntity2(orderItems);
		
		//(3)获取合同商品条目的品牌,型号,基本参数
		//List<ContractOrderItemDisplayBean> contractOrderItemDispList=new ArrayList<ContractOrderItemDisplayBean>();
		List<Map<String,Object>> contractOrderItemDispList=new ArrayList<Map<String,Object>>();
		for(ContractOrderItemBean contractItem:contractOrderItemBeanList){
			ContractOrderItemDisplayBean dispBean=new ContractOrderItemDisplayBean();
			//(1)加入contractOrderBean
			dispBean.setContractItem(contractItem);			
			
			//(2)查询SPU 准备查询数据
			Item spu=itemService.selectByPrimaryKey(contractItem.getItemId());
			//(3)查询型号			
			dispBean.setModel(spu.getModel());
			//dispBean.setModel("加入字段");
			//(2)查询品牌
			Brand brand=brandService.selectByPrimaryKey(spu.getBrand());
			dispBean.setBrandName(brand.getBrandName());			
			//(4)查询基本参数
			/*String spuNormAttr=spu.getAttributes();  //关键属性
			//String spuSaleAttr=spu.getAttrSale();  //销售属性
			String normAttrStr=getAttrStr(spuNormAttr);
			
			Sku sku=skuService.selectByPrimaryKey(contractItem.getSkuId());
			String skuAttr=sku.getAttributes();
			String saleAttrStr=getAttrStr(skuAttr);
			
			String parmStr="";
			if(StringUtil.isNotEmpty(normAttrStr)){
				parmStr=parmStr+normAttrStr;
				if(StringUtil.isNotEmpty(saleAttrStr)){
					parmStr=parmStr+","+saleAttrStr;
				}
			}
			else{
				parmStr=saleAttrStr;
			}
			
			dispBean.setParms(parmStr); //设置技术参数
			*/
			Sku sku=skuService.selectByPrimaryKey(contractItem.getSkuId());			
			dispBean.setParms(sku.getSkuShortSpec()); //设置简单技术参数
			
			
			//(5)加入列表
			contractOrderItemDispList.add(EntityToMap.ConvertObjToMap(dispBean));  //此处将对象转换成map
		}
		
		
		
		
		
		//计算合同总额
		BigDecimal contractItemTotal=calcContractOrderItemTotalPrice(contractOrderItemBeanList);  //计算合同总额
		String totalCN= NumberToCN.number2CNMontrayUnit(contractItemTotal);
		
		//(1)乙方信息
		CompanyInfo companyInfo=companyInfoService.selectByPrimaryKey(1L);
		model.addAttribute("companyInfo", companyInfo);
		
		//(3)订单信息  用于获取送货地址
		Orders order=orderService.selectOrderByOrderNo(contractOrderItemBeanList.get(0).getOrderId());
		model.addAttribute("order", order);
		
		//(4)甲方信息
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		model.addAttribute("agent", agent);
		
		model.addAttribute("attrValue", attrValueMap);  //合同属性
		model.addAttribute("contractItemList", contractOrderItemDispList);  //合同商品列表
		model.addAttribute("contractItemTotal",contractItemTotal);  //合同总金额 
		model.addAttribute("contractItemTotalCN",totalCN);  //合同中文金额
		model.addAttribute("now", new Date());
		
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_preview";
	}
	
	
	/**
	 * @Description 打开合同(打开已经生成的合同)
	 * @param id  合同ID(PK)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/open")
	public String contract_open(long id,Model model){
		//(1)乙方信息
		CompanyInfo companyInfo=companyInfoService.selectByPrimaryKey(1L);
		model.addAttribute("companyInfo", companyInfo);
		
		//(2)合同信息
		Contract contract=contractService.selectByPrimaryKey(id);
		model.addAttribute("contract", contract);
		
		//(3)订单信息  用于获取送货地址
		Orders order=orderService.selectOrderByOrderNo(contract.getOrderNo());
		model.addAttribute("order", order);
		
		//(4)甲方信息
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		model.addAttribute("agent", agent);
		
		//(5)合同商品列表
		List<Map<String,Object>> contractItemList=contractItemsService.selectItemsByContractNo(contract.getContractNo());
		model.addAttribute("contractItemList", contractItemList);  //合同商品列表
		
		//(6)合同属性
		List<Map<String,String>> contractAttrValList=contractAttributeService.selectAttrValByContractId(id);
		Map<String,String> attrValueMap=this.repackage(contractAttrValList); //包装成map
		model.addAttribute("attrValue", attrValueMap);  //合同属性
		
		BigDecimal contractItemTotal=calcContractItemTotalPrice(contractItemList);  //计算合同优惠后总额
		String totalCN= NumberToCN.number2CNMontrayUnit(contractItemTotal); //转换成中文金额
		
		
		//(7)统计信息
		model.addAttribute("contractItemTotal",contractItemTotal);
		model.addAttribute("contractItemTotalCN",totalCN);
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_open";
	}
	
	/**
	 * @Description 编辑合同(编辑合同)
	 * @param id  合同ID(PK)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String contract_edit(long id,Model model){
		//(1)合同信息
		Contract contract=contractService.selectByPrimaryKey(id);
		model.addAttribute("contract", contract);
		
		//(1.1)查询订单
		Orders order=orderService.selectOrderByOrderNo(contract.getOrderNo());
		//(1.2)查询代理商
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		model.addAttribute("agent", agent);
		
		//(2)合同商品列表
		List<Map<String,Object>> contractItemList=contractItemsService.selectItemsByContractNo(contract.getContractNo());
		model.addAttribute("contractItemList", contractItemList);  //合同商品列表
		
		//(3)合同属性
		List<Map<String,String>> contractAttrValList=contractAttributeService.selectAttrValByContractId(id);
		Map<String,Object> attrValueMap=repackageAttr(contractAttrValList); //包装成map
		model.addAttribute("attrValue", attrValueMap);  //合同属性
		
		//(2)获取合同属性
		//TODO 查询条件：template_id=1  此处暂时直接查询，而后进行修改		
		long templateId=1L;
		List<ContractAttribute> contractAttrList=contractAttributeService.selectAttributesByTemplateId(templateId);
		model.addAttribute("contractAttrList", contractAttrList);
		
		return RESPONSE_THYMELEAF_BACK_ORDER+"contract_edit";
	}
	
	/**
	 * @Description 更新合同条目折减
	 * @param itemId 合同条目id(pk)
	 * @param discount 折减
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/itemupdate")
	@ResponseBody
	public Object contract_item_update(long itemId,BigDecimal discount,Model model){
		ContractItems record=new ContractItems();
		record=contractItemsService.selectByPrimaryKey(itemId);		
		record.setDiscountPrice(discount);  //折减
		record.setPayPrice(record.getPrimitivePrice().subtract(discount));
		BigDecimal num=new BigDecimal(record.getNum());
		record.setPayPriceTotal(record.getPayPrice().multiply(num));
		contractItemsService.updateByPrimaryKeySelective(record);
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	/**
	 * @Description 更新合同
	 * @param contractAttrVals
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/attrupdate")
	@ResponseBody
	public Object contract_attr_update(String contractAttrVals,Model model){
		List<ContractAttrValueBean> contractAttrValueList=getEntity1(contractAttrVals);  //合同属性列表
		updateContractAttr(contractAttrValueList);
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	/**
	 * @Description 创建新合同
	 * @param contractAttrVals  合同属性（JSON格式）
	 * @param orderItems  合同商品列表（JSON格式）
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/create")
	@ResponseBody
	public Object contract_create(String contractAttrVals,String orderItems,Model model,HttpServletRequest request){
		
		List<ContractAttrValueBean> contractAttrValueList=getEntity1(contractAttrVals);  //合同属性列表
		//Map<String,String> attrValueMap=  convertAttrValueList(contractAttrValueList);
		
		List<ContractOrderItemBean> contractOrderItemBeanList=getEntity2(orderItems);  //合同商品列表
		
		//HttpSession session = request.getSession();
		//User user = (User) session.getAttribute(SessionConstants.USER);
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		//(1)创建合同
		String orderNo=getOrderNo(contractOrderItemBeanList.get(0));   //获取订单号
		long contractTemplateId=getTemplateId(contractAttrValueList.get(0));  //获取合同模板ID
		
		String contractNo=getContractNo();  //生成合同号
		Long contractId=createContract(contractNo,contractTemplateId,orderNo,user.getId());
		
		//(2)插入合同属性值
		addContractAttrValues(contractId,contractAttrValueList);
		
		//(3)插入合同商品条目
		addContractItems(contractOrderItemBeanList,contractNo);
		
		//(4)在订单中加入合同信息
		updateOrderContract(orderNo,contractId,contractNo);
		
		return RequestResultUtil.getResultAddSuccess();
	}
	
	/**
	 * @Description 后台置合同的状态
	 * @param orderId
	 * @param contractId
	 * @param status
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/setstatus")
	@ResponseBody
	public Object contract_set_setatus(long orderId,long contractId,byte status, Model model,HttpServletRequest request){
		//HttpSession session = request.getSession();
		//User user = (User) session.getAttribute(SessionConstants.USER);
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		setContractStatus(orderId,contractId,status,user.getId(),BACK_CONTRACT_CONFIRM);
		
		//如果设置合同状态为执行完毕，则记录账本（公司账本和个人账本）
		if(Integer.valueOf(status)==ContractStateType.FINISHED){
			this.addAccountCompany(orderId, contractId);//记录公司账本
			this.addAccountPersonal(orderId, contractId);//记录个人账本
		}
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	/**
	 * @Description 后台置合同的状态
	 * @param orderId
	 * @param contractId
	 * @param status
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/frontsetstatus")
	@ResponseBody
	public Object contract_front_set_status(long orderId,long contractId,byte status, Model model,HttpServletRequest request){
		//HttpSession session = request.getSession();
		//User user = (User) session.getAttribute(SessionConstants.USER);
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		setContractStatus(orderId,contractId,status,user.getId(),FRONT_CONTRACT_CONFIRM);
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	
	
	private  final  int BACK_CONTRACT_CONFIRM=2;
	private  final 	int FRONT_CONTRACT_CONFIRM=1; 
	
	/**
	 * @Description 根据属性:属性值对返回属性名:属性值名  字符串
	 * @param attrs  属性id:属性值id,属性id:属性值id pairs
	 * @return 返回  属性名:属性值名,属性名:属性值名
	 */
	private String getAttrStr(String attrs){
		if(StringUtil.isEmpty(attrs)) return  "";		
		//先按","号进行分隔
		String parmStr="";
		String[] keyValues=attrs.split(",");
		for(String keyValue:keyValues){
			//按":"进行分隔
			String[] ids=keyValue.split(":");
			String keyName=attributeService.selectByPrimaryKey(Long.parseLong(ids[0])).getAttrName();
			String valueName=attributeValueService.selectByPrimaryKey(Long.parseLong(ids[1])).getValueName();
			if(StringUtil.isEmpty(parmStr)){
				parmStr=keyName+":"+valueName;
			}
			else{
				parmStr=parmStr+","+keyName+":"+valueName;
			}
		}
		return parmStr;
	}


	/**
	 * @Description 设置合同状态
	 * @param orderId
	 * @param contractId
	 * @param status
	 * @param userId
	 */
	private void setContractStatus(long orderId,long contractId,byte status,long userId,int frontOrBack){
		Orders order=new Orders();
		order.setId(orderId);
		order.setContractState(status);
		orderService.updateByPrimaryKeySelective(order);
		
		Contract contract=new Contract();
		contract.setId(contractId);
		
		if(frontOrBack==FRONT_CONTRACT_CONFIRM){  //前台合同确认
			contract.setConfirmUserFirstParty(userId);
			contract.setConfirmDateFirstParty(new Date());
		}
		else{  //后台合同确认
			contract.setConfirmUserSecondParty(userId);
			contract.setConfirmDateSecondParty(new Date());
		}
		
		
		
		
		contract.setContractStatus(status);  //合同状态
		
		contractService.updateByPrimaryKeySelective(contract);
	}
	
	
	/**
	 * @Description (TODO这里用一句话描述这个方法的作用)
	 * @param contractAttrValueList
	 */
	private void updateContractAttr(List<ContractAttrValueBean> contractAttrValueList){
		for(ContractAttrValueBean attrx:contractAttrValueList){
			ContractAttrValue record=new ContractAttrValue();
			record.setId(attrx.getId());
			record.setAttrValue(attrx.getAttrValue());
			contractAttrValueService.updateByPrimaryKeySelective(record);
		}
	}
	
	
	private Map<String,String> repackage(List<Map<String,String>> list){
		Map<String,String> map=new HashMap<String,String>();
		for(Map<String,String> attrValue:list){
			map.put(attrValue.get("attr_name"), attrValue.get("attr_value"));
		}
		return map;
	}
	
	/**
	 * @Description 编辑合同属性时，合同属性列表
	 * @param list 合同属性列表
	 * @return  合同属性map 列表     attr_namex:attr  
	 * 						  attr(attrVal:attr_value;
	 * 								id:合同属性id
	 * 								)
	 */
	private Map<String,Object> repackageAttr(List<Map<String,String>> list){
		Map<String,Object> map=new HashMap<String,Object>();
		for(Map<String,String> attrValue:list){
			
			Map<String,Object> valAndId=new HashMap<String,Object>();
			valAndId.put("val", attrValue.get("attr_value"));
			valAndId.put("id", attrValue.get("id"));
			
			map.put(attrValue.get("attr_name"), valAndId);
			
		}
		return map;
	}
	
	
	
	/**
	 * @Description 生成合同后，更新相关订单合同信息
	 * @param orderNo   订单编号
	 * @param contractId  合同's id (pk)
	 * @param contractNo  合同编号
	 */
	private void updateOrderContract(String orderNo,long contractId,String contractNo){
		//根据订单号获取订单id(pk)
		long id= orderService.getIdByOrderNo(orderNo);  //根据订单号获取订单ID
		Orders order=new Orders();
		
		order.setId(id);
		order.setContractNo(contractNo);
		order.setContractId(contractId);
		order.setContractState((byte)ContractStateType.CREATED_YES); //状态：已建合同
		
		orderService.updateByPrimaryKeySelective(order);		
	}
	
	
	/**
	 * @Description 增加合同属性
	 * @param contractId  合同ID
	 * @param contractAttrValueList 合同属性列表
	 */
	private void addContractAttrValues(Long contractId,List<ContractAttrValueBean> contractAttrValueList){
		for(ContractAttrValueBean attrValue:contractAttrValueList){
			
			ContractAttrValue record=new ContractAttrValue();
			
			record.setTemplateId(attrValue.getTemplateId());  //模板id
			record.setAttrId(attrValue.getId());  //属性id
			record.setAttrValue(attrValue.getAttrValue());  //属性值			
			record.setContractId(contractId);  //合同id(非合同号)
			
			
			contractAttrValueService.insertSelective(record);
		}
	}
	
	
	
	/**
	 * @Description 增加合同行项目（商品列表）
	 * @param orderItemList 商品列表
	 * @param contractNo  合同号
	 */
	private void addContractItems(List<ContractOrderItemBean> orderItemList,String contractNo){
		for(ContractOrderItemBean orderItem:orderItemList){
			
			
			/*
			 "create_time":"2017-06-27T17:45:12.000+08:00",
							"picture_url":"/static/images/iphone_index.png",
							"item_id":1,
							"num":2,
							"sku_id":2,
							"picture_id":2,
							"picture_status":1,
							"primitive_price":222,
							"sort_number":1,
							"sku_name":"Apple8新品颜色:RED版本:V2","id":59,
							"order_id":"1000000937189443",
							"cid":13,
							"discount_price":0,
							"pay_price":222,
							"pay_price_total":444
							highest_price
							lowest_price
							hard_cost_price
							is_plan_product
			 */
			
			
			ContractItems record=new ContractItems();
			
			record.setCid(orderItem.getCid());
			record.setItemId(orderItem.getItemId());
			record.setSkuId(orderItem.getSkuId());
			record.setSkuName(orderItem.getSkuName());
			record.setOrderId(orderItem.getOrderId());
			record.setContractNo(contractNo);
			
			record.setHighestPrice(orderItem.getHighest_price());//最高限价
			record.setLowestPrice(orderItem.getLowest_price());//最低限价
			record.setHardCostPrice(orderItem.getHard_cost_price());//硬成本价格
			record.setIsPlanProduct(orderItem.getIs_plan_product());//是否是方案性产品（1：是；2：否；）
			
			record.setPrimitivePrice(orderItem.getPrimitivePrice());  //原始价
			record.setDiscountPrice(orderItem.getDiscountPrice());  //价格折减
			record.setPayPrice(orderItem.getPayPrice());  //实际支付价格
			record.setNum(orderItem.getNum());  //商品数量
			record.setPayPriceTotal(orderItem.getPayPriceTotal());  //小计
			
			//(1)查询SPU 准备查询数据
			Item spu=itemService.selectByPrimaryKey(orderItem.getItemId());
			
			//(2)查询型号		
			record.setModel(spu.getModel());			
			//(3)查询品牌
			Brand brand=brandService.selectByPrimaryKey(spu.getBrand());
			record.setBrandName(brand.getBrandName());			
			//(4)查询基本参数
			/*String spuNormAttr=spu.getAttributes();  //关键属性
			//String spuSaleAttr=spu.getAttrSale();  //销售属性
			String normAttrStr=getAttrStr(spuNormAttr);
			
			Sku sku=skuService.selectByPrimaryKey(orderItem.getSkuId());
			String skuAttr=sku.getAttributes();
			String saleAttrStr=getAttrStr(skuAttr);
			
			String parmStr="";
			if(StringUtil.isNotEmpty(normAttrStr)){
				parmStr=parmStr+normAttrStr;
				if(StringUtil.isNotEmpty(saleAttrStr)){
					parmStr=parmStr+","+saleAttrStr;
				}
			}
			else{
				parmStr=saleAttrStr;
			}
			record.setParms(parmStr); //设置技术参数
			*/
			Sku sku=skuService.selectByPrimaryKey(orderItem.getSkuId());
			record.setParms(sku.getSkuShortSpec()); //设置简单技术参数
			
			
			record.setCreateTime(new Date());
			
			contractItemsService.insertSelective(record);
		}
	}
	
	
	
	
	/**
	 * @Description 取得合同号
	 * @param contractItem  合同条目对象
	 * @return  合同编号
	 */
	private String getOrderNo(ContractOrderItemBean contractItem){
		return contractItem.getOrderId();
	}
	
	/**
	 * @Description  取得合同模板ID
	 * @param attrValue  合同属性值对象
	 * @return 合同模板ID
	 */
	private long getTemplateId(ContractAttrValueBean attrValue){
		return attrValue.getTemplateId();
	}
	
	/**
	 * @Description 创建合同
	 * @param contractNo 合同号
	 * @param contractTemplateId  合同模板id
	 * @param orderNo 订单号
	 * @param createUserId 合同创建者id
	 * @return  合同id
	 */
	private Long createContract(String contractNo,Long contractTemplateId,String orderNo,Long createUserId){
		//查询订单（根据订单号）
		Orders order=orderService.selectOrderByOrderNo(orderNo);  //读取订单
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());  //代理商
		
		
		
		//生成合同对象
		Contract contract=new Contract(); 
		
		//设置合同属性
		contract.setContractTemplateId(contractTemplateId);
		contract.setOrderNo(orderNo);
		contract.setOrderId(order.getId());		
		contract.setContractNo(contractNo);
		//创建用户及时间
		contract.setCreateDate(new Date());
		contract.setCreateUser(createUserId);
		
		if(agent!=null){
			contract.setAgentId(agent.getExtendId());  //设置代理商id
		}
		contract.setContractStatus((byte)ContractStateType.CREATED_YES);
		
		//插入记录
		contractService.insertSelective(contract);
		
		return contract.getId();
	}
	
	/**
	 * @Description 生成合同号
	 * 	合同编号格式为：LM-年（四位）-月（两位）-序号（四位）
	 * @return  返回合同号
	 */
	private String getContractNo(){
		return contractService.getContractNo();
	}
	
	/**
	 * @Description 计算合同总金额（优惠前）
	 * @param itemList  合同条目商品列表
	 * @return  合同总金额
	 */
	private BigDecimal calcContractOrderItemTotalPrice(List<ContractOrderItemBean> itemList){
		BigDecimal total=new BigDecimal(0.00);
		for(ContractOrderItemBean item:itemList){
			total=total.add(item.getPayPriceTotal());
		}
		return total;
	}
	
	/**
	 * @Description 计算合同总金额（优惠后）
	 * @param itemList  合同条目商品列表
	 * @return  合同总金额
	 */
	private BigDecimal calcContractItemTotalPrice(List<Map<String,Object>> itemList){
		BigDecimal total=new BigDecimal(0.00);
		for(Map<String,Object> item:itemList){
			//Double subTotal=Double.parseDouble(item.get("pay_price_total"));
			total=total.add((BigDecimal)item.get("pay_price_total"));
		}
		return total;
	}
	
	
	/**
	 * @Description 将合同属性list转换为map
	 * @param list
	 * @return
	 */
	private Map<String,String> convertAttrValueList(List<ContractAttrValueBean> list){
		Map<String,String> map=new HashMap<String,String>();
		for(ContractAttrValueBean attrValue:list){
			map.put(attrValue.getAttrName(), attrValue.getAttrValue());
		}
		return map;
	}
	
	
	/**
	 * @Description 自JSON生成对象
	 * @param resp
	 * @return
	 */
	private List<ContractAttrValueBean> getEntity1(String resp) {
		List<ContractAttrValueBean> list = JSONArray.parseArray(resp,ContractAttrValueBean.class);
		return list; 
	} 
	
	/**
	 * @Description 自JSON生成对象
	 * @param resp
	 * @return
	 */
	private List<ContractOrderItemBean> getEntity2(String resp) {		
		List<ContractOrderItemBean> list =JSONArray.parseArray(resp,ContractOrderItemBean.class);    
        return list;
	} 
	
	/**
	 * 增加公司账本记录
	 * 
	 * @param orderId
	 * @param contractId
	 */
	private void addAccountCompany(long orderId,long contractId){
		
		Orders order = orderService.selectByPrimaryKey(orderId);
		Contract contract = contractService.selectByPrimaryKey(contractId);
		
		int type = 0;//类型
		Date createTime = new Date();
		//在账本中增加业绩
		type = AccountItemType.PERFORMANCE_FEE;//业绩
		AccountCompany accountCompany = this.getAccountCompanyEntity(order, contract, this.getAmount(contract.getContractNo(), type), type, createTime);
		accountCompanyService.insertSelective(accountCompany);
		//在账本中增加差价
		type = AccountItemType.PRICE_DIFFERENCE_FEE;//差价
		accountCompany = this.getAccountCompanyEntity(order, contract, this.getAmount(contract.getContractNo(), type), type, createTime);
		accountCompanyService.insertSelective(accountCompany);
		//在账本中增加纯利润
		type = AccountItemType.NET_PROFIT_FEE;//纯利润
		accountCompany = this.getAccountCompanyEntity(order, contract, this.getAmount(contract.getContractNo(), type), type, createTime);
		accountCompanyService.insertSelective(accountCompany);
	}
	/**
	 * 根据type分别计算业绩、差价和纯利润
	 * 
	 * @param contractNo
	 * @param type
	 * @return
	 */
	private BigDecimal getAmount(String contractNo, int type){
		try {
			ContractItems contractItems = new ContractItems();
			contractItems.setContractNo(contractNo);
			List<ContractItems> contractItemsList = contractItemsService.select(contractItems);
			if(contractItemsList!=null && !contractItemsList.isEmpty()){
				BigDecimal amount = new BigDecimal("0.00");
				switch (type) {
				case AccountItemType.PERFORMANCE_FEE://业绩
					for(ContractItems temp : contractItemsList){
						amount = amount.add(temp.getPayPriceTotal());
					}
					break;
				case AccountItemType.PRICE_DIFFERENCE_FEE://差价
					for(ContractItems temp : contractItemsList){
						//支付总价-最低限价*商品数量
						BigDecimal price = temp.getLowestPrice().multiply(new BigDecimal(temp.getNum().toString()));
						price = temp.getPayPriceTotal().subtract(price);
						amount = amount.add(price);
					}
					break;
				case AccountItemType.NET_PROFIT_FEE://纯利润
					for(ContractItems temp : contractItemsList){
						//支付总价-硬成本价格*商品数量
						BigDecimal price = temp.getHardCostPrice().multiply(new BigDecimal(temp.getNum().toString()));
						price = temp.getPayPriceTotal().subtract(price);
						amount = amount.add(price);
					}
					break;

				default:
					System.out.println("type为null	type:"+type);
					break;
				}
				return amount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal("0.00");
	}
	/**
	 * 创建公司账本实体类
	 * 
	 * @param order
	 * @param contract
	 * @param amount
	 * @param type
	 * @param createTime
	 * @return
	 */
	private AccountCompany getAccountCompanyEntity(Orders order, Contract contract, BigDecimal amount, int type, Date createTime){
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		AccountCompany accountCompany = new AccountCompany();
		accountCompany.setCustId(contract.getAgentId());
		accountCompany.setOrderId(order.getId());
		accountCompany.setOrderNo(order.getOrderId());
		accountCompany.setContractId(contract.getId());
		accountCompany.setContractNo(contract.getContractNo());
		accountCompany.setAmount(amount);
		accountCompany.setType(type);
		accountCompany.setOperatorId(user.getId());
		accountCompany.setOperatorName(user.getNickname());
		accountCompany.setCreateTime(createTime);
		return accountCompany;
	}
	
	/**
	 * 增加个人账本记录（IS/OS）
	 * @param orderId
	 * @param contractId
	 */
	private void addAccountPersonal(long orderId,long contractId){
		Orders order = orderService.selectByPrimaryKey(orderId);
		Contract contract = contractService.selectByPrimaryKey(contractId);
		
		int type = 0;//类型
		Date createTime = new Date();
		
		long custId = this.getCustId(order.getBuyerId());
		
		//查询绑定关系列表
		CustLockRel custLockRel = new CustLockRel();
		custLockRel.setCustId(custId);
		List<CustLockRel> tempList = agentBindService.select(custLockRel);
		for(CustLockRel temp : tempList){
			Long bindUserId = temp.getBindUserId();
			Long roleId = temp.getRoleId();
			//在账本中增加业绩
			type = AccountItemType.PERFORMANCE_FEE;//业绩
			AccountPersonal accountPersonal = this.getAccountPersonalEntity(order, contract, this.getAmount(contract.getContractNo(), type), type, bindUserId, roleId, createTime);
			accountPersonalService.insertSelective(accountPersonal);
			//在账本中增加差价
			type = AccountItemType.PRICE_DIFFERENCE_FEE;//差价
			accountPersonal = this.getAccountPersonalEntity(order, contract, this.getAmount(contract.getContractNo(), type), type, bindUserId, roleId, createTime);
			accountPersonalService.insertSelective(accountPersonal);
		}
	}
	/**
	 * 查询代理商信息ID
	 * @param buyerId
	 * @return
	 */
	private Long getCustId(Long buyerId){
		long custId = 0l;
		User user = userService.selectByPrimaryKey(buyerId);
		if(user!=null){
			if(user.getParentId()==null || user.getParentId()==0){
				UserExtends userExtends = new UserExtends();
				userExtends.setUserId(user.getId());
				List<UserExtends> tempList = userExtendsService.select(userExtends);
				if(tempList!=null && !tempList.isEmpty()){
					custId = tempList.get(0).getExtendId();
				}
			}else{
				user = userService.selectByPrimaryKey(user.getParentId());
				if(user.getParentId()==null || user.getParentId()==0){
					UserExtends userExtends = new UserExtends();
					userExtends.setUserId(user.getId());
					List<UserExtends> tempList = userExtendsService.select(userExtends);
					if(tempList!=null && !tempList.isEmpty()){
						custId = tempList.get(0).getExtendId();
					}
				}
			}
		}else{
			System.out.println("订单中的购买者信息为null");
		}
		return custId;
	}
	/**
	 * 创建个人账本实体类
	 * @param order
	 * @param contract
	 * @param amount
	 * @param type
	 * @param bindUserId
	 * @param roleId
	 * @param createTime
	 * @return
	 */
	private AccountPersonal getAccountPersonalEntity(Orders order, Contract contract, BigDecimal amount, int type, long bindUserId, long roleId, Date createTime){
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		AccountPersonal accountPersonal = new AccountPersonal();
		accountPersonal.setCustId(contract.getAgentId());
		accountPersonal.setOrderId(order.getId());
		accountPersonal.setOrderNo(order.getOrderId());
		accountPersonal.setContractId(contract.getId());
		accountPersonal.setContractNo(contract.getContractNo());
		accountPersonal.setItemId(0l);
		accountPersonal.setSkuId(0l);
		accountPersonal.setAmount(amount);
		accountPersonal.setType(type);
		accountPersonal.setBindUserId(bindUserId);
		accountPersonal.setRoleId(roleId);
		accountPersonal.setOperatorId(user.getId());
		accountPersonal.setOperatorName(user.getNickname());
		accountPersonal.setCreateTime(createTime);
		return accountPersonal;
	}
	
}
