package com.ecp.back.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IAgentService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName UserAgentController
 * @Description 签约客户-OS/IS绑定控制器
 * @author Administrator
 * @Date 2017年6月17日 上午11:37:13
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/agent_bind")
public class AgentBindController {
	private  final Logger log = Logger.getLogger(getClass());
	
	private static final  String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/agent_bind/";
	private static final  String RESPONSE_JSP = "jsps/front/";

	private static final int PAGE_SIZE = 8;
	
	private static final String OUTSIDE_ROLE="Outside Sales";
	private static final String INSIDE_ROLE="Inside Sales";

	

	@Autowired
	IUserAgentService userAgentService;
	@Autowired
	IUserService userService;
	@Autowired
	IAgentService agentService; 
	@Autowired
	IAgentBindService agentBindService;  //代理商绑定

	/**
	 * @Description 显示-签约客户列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String user_agent_show(Model model) {
		return RESPONSE_THYMELEAF_BACK + "user_agent_show";
	}
	
	/**
	 * @Description 
	 * @param pageNum 查询页号
	 * @param pageSize 页大小
	 * @param searchTypeValue 查询字段
	 * 		（以整形表示：0-选择查询条件；1：企业名称；2：负责人姓名；3：电话号码）
	 * @param condValue 查询条件值
	 * @param provinceName 	省份名称
	 * @param cityName	   	市名称
	 * @param countyName	区(县)名称
	 * @return 
	 */
	@RequestMapping(value = "/agenttable")
	public String user_agent_agenttable(Integer pageNum, 
										Integer pageSize,
										Integer searchTypeValue,
										String  condValue,
										String  provinceName,
										String 	cityName,
										String 	countyName,
										Model model) {
		
		if(pageNum==null || pageNum==0)
		{
			pageNum=1;
			pageSize=PAGE_SIZE;
		}
		//置默认值
		if(searchTypeValue==null){
			searchTypeValue=0;
			condValue="";
		}
		
		// 查询并分页(PageHelper)		
		PageHelper.startPage(pageNum, pageSize);  
			
		//根据查询类型、条件值,区域代码条件进行查询		
		List<UserExtends> userAgents = userAgentService.searchUserAgent(searchTypeValue, "%"+condValue+"%",provinceName,cityName,countyName);
		PageInfo<UserExtends> pageInfo = new PageInfo<>(userAgents);// (使用了拦截器或是AOP进行查询的再次处理)
				
		
		//回传数据
		model.addAttribute("pageInfo", pageInfo);  					//分页信息
		model.addAttribute("searchTypeValue", searchTypeValue);  	//查询字段值
		model.addAttribute("condValue", condValue);  				//查询条件值
		model.addAttribute("provinceName", provinceName);			//省份名称
		model.addAttribute("cityName", cityName);					//城市名称
		model.addAttribute("countyName", countyName);				//区(县)名称
		
		//查询签约客户的OS及IS并置于回传数据中.
		// map结构如下:
		// 	"agent":agent对象
		//	"os":os姓名串
		//	"is":is姓名串
		List<Map<String,Object>> agentBinds=new ArrayList<Map<String,Object>>();	
		for(int i=0;i<userAgents.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("agent", userAgents.get(i));
			
			String osNames=getAgentOs(userAgents.get(i).getExtendId());
			map.put("os", osNames);
			
			String isNames=getAgentIS(userAgents.get(i).getExtendId());
			map.put("is",isNames);
			
			agentBinds.add(map);
		}

		model.addAttribute("agentBinds", agentBinds);  				//签约客户与OS/IS绑定

		return RESPONSE_THYMELEAF_BACK + "user_agent_table";
	}
	
	
	/**
	 * @param agentId 签约代理商ID
	 * @return 返回签约代理商OS姓名,如果有多个,以逗号进行分隔
	 */
	private String getAgentOs(long agentId){
		List<Map<String,Object>> saleList1=agentBindService.getSales(OUTSIDE_ROLE);
		return "";
	}
	
	/**
	 * @param agentId 签约代理商ID
	 * @return 返回签约代理商IS姓名,如果有多个,以逗号进行分隔
	 */
	private String getAgentIS(long agentId){
		List<Map<String,Object>> saleList1=agentBindService.getSalesByAgentId(agentId, OUTSIDE_ROLE);		
		return "";
	}
	
	
	
	
	/**
	 * @Description 分配帐号（帐号默认为有效状态）
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/dispatch" ,method=RequestMethod.POST)
	@ResponseBody
	public Object user_agent_dispatch(HttpServletRequest request){
		
		String loginName=request.getParameter("loginName");
		String nickName=request.getParameter("nickName");
		String password=request.getParameter("password");
		long agentId=Long.parseLong(request.getParameter("agentId"));
		
		
		User user=new User();
		user.setCreatedTime(new Date());
		user.setUsername(loginName);
		user.setNickname(nickName);
		String md5Pass=genMD5Password(loginName,password);
		user.setPassword(md5Pass);
		user.setStatus(AccountStatusType.VALID);
		user.setType(UserType.AGENT);  //帐号类型
		user.setDeleted(DeletedType.NO);//删除类型：1=未删除，2=已删除
		
		int row =userService.insertSelective(user);
		if(row>0){
			UserExtends agent=new UserExtends();
			agent.setExtendId(agentId);
			agent.setUserId(user.getId());
			agent.setAccountState(AccountStatusType.VALID);
			userAgentService.updateByPrimaryKeySelective(agent);
		}
		
		return RequestResultUtil.getResultUpdateSuccess();
		
	}*/
	
	/**
	 * @Description 签约客户-详情（修改）
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	public String user_agent_detail(@PathVariable("id") long extendId, Model model) {
	
		UserExtends agent = userAgentService.selectByPrimaryKey(extendId);
		model.addAttribute("agent", agent);
	
		return RESPONSE_THYMELEAF_BACK + "user_agent_detail";
	}
	
	/**
	 * @Description 显示签约客户-OS/IS绑定界面
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "/bind/{id}")
	public String showAgentBind(@PathVariable("id") long extendId, Model model) {
		
		//查询所有OS角色用户
		List<Map<String,Object>> outsideSales=agentBindService.getSales(OUTSIDE_ROLE);
		//查询所有IS角色用户
		List<Map<String,Object>> insideSales=agentBindService.getSales(INSIDE_ROLE);
		
		model.addAttribute("outsideSales",outsideSales);
		model.addAttribute("insideSales",insideSales);
	
		//查询签约客户所绑定的OS/IS
		List<Map<String,Object>> agentBindedOS= agentBindService.getSalesByAgentId(extendId,OUTSIDE_ROLE);
		List<Map<String,Object>> agentBindedIS= agentBindService.getSalesByAgentId(extendId,INSIDE_ROLE);
		//组装成前台需要的格式
		List<Map<String,Object>> agentBindList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<agentBindedOS.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("sale", agentBindedOS.get(i));
			map.put("type", OUTSIDE_ROLE);
			agentBindList.add(map);
		}
		for(int i=0;i<agentBindedIS.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("sale", agentBindedIS.get(i));
			map.put("type", INSIDE_ROLE);
			agentBindList.add(map);
		}		
		
		model.addAttribute("agentBindList", agentBindList);
		model.addAttribute("agentId",extendId);
	
		return RESPONSE_THYMELEAF_BACK + "agent_bind_osis";
	}
	
	
	/** 
	* @Title: BindSalesToAgent 
	* @Description: 绑定签约客户与OS/IS 
	* @param @param parms 绑定参数,格式json  {"agentId":1,"saleIdList":[1,2]}
	* @param @param model
	* @param @return     
	* @return Object    返回类型 JSON格式 
	* @throws 
	*/
	@RequestMapping(value = "/binduser")
	@ResponseBody
	public Object BindSalesToAgent(@RequestBody String parms, Model model) {
		log.info("bind sales---parms"+parms);
		
		//解析参数:获取agent id
		JSONObject parm=JSON.parseObject(parms);
		long agentId=parm.getLongValue("agentId");
		
		//解析参数:获取所要绑定的用户ID 数组.
		JSONArray idArr=JSON.parseArray(parm.getString("saleIdList"));
		for(int i=0;i<idArr.size();i++){
			this.agentBindService.addBindAgentToUser(agentId, idArr.getLongValue(i));
		}
		
		return RequestResultUtil.getResultUpdateSuccess();		
	}
	
	
	/** 
	* @Title: BindSalesToAgent1 
	* @Description: 此函数保留,用于展示参数传递
	* @param @param agentId
	* @param @param saleIdList
	* @param @param model
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/bindsales1")
	@ResponseBody
	public Object BindSalesToAgent1(long agentId,String saleIdList, Model model) {
		/*log.info("test2");
		log.info("agentId"+agentId);
		log.info("saleIdList"+saleIdList);*/
		
		return RequestResultUtil.getResultUpdateSuccess();		
	}
	
	
	

	/**
	 * @Description 分配帐号（帐号默认为有效状态）
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/setstate" ,method=RequestMethod.POST)
	@ResponseBody
	public Object user_agent_set_state(long agentId,long userId,int accountState, HttpServletRequest request){
		UserExtends agent=new UserExtends();
		agent.setExtendId(agentId);
		agent.setAccountState(accountState);
		userAgentService.updateByPrimaryKeySelective(agent);
		
		User user=new User();
		user.setId(userId);
		user.setStatus(accountState);
		userService.updateByPrimaryKeySelective(user);
		
		return RequestResultUtil.getResultUpdateSuccess(); 
		
		
		
	}*/
	
	/**
	 * @Description 插入签约客户
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Object user_agent_insert(HttpServletRequest request, UserExtends agent) {
		System.out.println(agent.getBusinessLicencePicSrc());
		//处理上传文件
		if (!this.processUploadFile(request, agent)) {
			return RequestResultUtil.getResultUploadWarn();
		}

		agent.setCreateDt(new Date());

		int row = userAgentService.addUserAgent(agent);
		if (row > 0) {
			return RequestResultUtil.getResultAddSuccess();
		}

		return RequestResultUtil.getResultAddWarn();
	}*/

	/**
	 * @Description 更新签约客户
	 * @param agent 签约客户对象
	 * @param request
	 * @return 
	 */
	/*@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object user_agent_update(UserExtends agent,HttpServletRequest request) {
		System.out.println(agent.getBusinessLicencePicSrc());
		//处理上传文件
		if (!this.processUploadFile(request, agent)) {
			return RequestResultUtil.getResultUploadWarn();
		}

		int row=userAgentService.updateByPrimaryKeySelective(agent);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}

		return RequestResultUtil.getResultUpdateWarn();
	}*/
	
}
