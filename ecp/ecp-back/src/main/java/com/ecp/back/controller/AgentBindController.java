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
import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.DeletedType;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.CustLockRel;
import com.ecp.entity.Role;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IAgentService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.StringUtil;

/**
 * @ClassName UserAgentController
 * @Description 签约客户-OS/IS绑定控制器
 * @author Administrator
 * @Date 2017年6月17日 上午11:37:13
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/agent-bind")
public class AgentBindController {
	private  final Logger log = Logger.getLogger(getClass());
	
	private static final  String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/agent_bind/";
	private static final  String RESPONSE_JSP = "jsps/front/";

	private static final int PAGE_SIZE = 8;
	

	@Autowired
	IUserAgentService userAgentService;
	@Autowired
	IUserService userService;
	@Autowired
	IAgentService agentService; 
	@Autowired
	IAgentBindService agentBindService;  //代理商绑定
	@Autowired
	IRoleService roleService;  //角色服务

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
	* @Title: showBindedUsers 
	* @Description: 显示-指定的签约客户绑定的user列表 
	* @param @param agentId  代理商ID
	* @param @param model
	* @param @return    绑定的用户列表页面 
	* @return String     
	* @throws 
	*/
	@RequestMapping(value = "/showbindedusers")
	public String showBindedUsers(long agentId,Model model) {
		//查询签约客户所绑定的OS/IS
		List<Map<String,Object>> agentBindedOS= agentBindService.getSalesByAgentId(agentId,RoleCodeConstants.OS);
		List<Map<String,Object>> agentBindedIS= agentBindService.getSalesByAgentId(agentId,RoleCodeConstants.IS);
		//组装成前台需要的格式
		List<Map<String,Object>> agentBindList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<agentBindedOS.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("user", agentBindedOS.get(i));
			map.put("type", "外部销售");
			agentBindList.add(map);
		}
		for(int i=0;i<agentBindedIS.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("user", agentBindedIS.get(i));
			map.put("type", "内部销售");
			agentBindList.add(map);
		}		
		
		model.addAttribute("agentBindList", agentBindList);
		
		
		return RESPONSE_THYMELEAF_BACK + "agent_binded_users";
	}
	
	
	
	/**
	 * @Description 显示签约客户企业列表.
	 * @param pageNum 查询页号
	 * @param pageSize 页大小
	 * @param searchTypeValue 查询字段
	 * 		（以整形表示：0-选择查询条件；1：企业名称；2：负责人姓名；3：电话号码;4:OS人员姓名;5:IS人员姓名）
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
		
		//确定范围
		List<Map<String,Object>> agentIdList=getAgentScope(searchTypeValue,condValue);  
		
		// 查询并分页(PageHelper)		
		PageHelper.startPage(pageNum, pageSize);
		//根据查询类型、条件值,区域代码条件进行查询		
		//List<UserExtends> userAgents = userAgentService.searchUserAgent(searchTypeValue, "%"+condValue+"%",provinceName,cityName,countyName);
		//审核状态:查询所有
		List<UserExtends> userAgents = userAgentService.searchUserAgent(searchTypeValue, 
				condValue,
				provinceName,
				cityName,
				countyName,
				agentIdList,
				(byte)0);
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
			
			List<Map<String,Object>> osUsers=getAgentOs(userAgents.get(i).getExtendId());
			map.put("osUsers", osUsers);
			
			List<Map<String,Object>> isUsers=getAgentIS(userAgents.get(i).getExtendId());
			map.put("isUsers",isUsers);
			
			agentBinds.add(map);
		}

		model.addAttribute("agentBinds", agentBinds);  				//签约客户与OS/IS绑定

		return RESPONSE_THYMELEAF_BACK + "user_agent_table";
	}
	
	/** 
		* @Title: batchUnbind 
		* @Description: 批量绑定 
		* @param @param parms
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/batchbind")
	@ResponseBody
	public Object batchbind(@RequestBody String parms) {
		
		JSONObject parm=JSON.parseObject(parms);
		
		//解析参数:获取所要绑定的用户ID 数组.
		JSONArray userArr=JSON.parseArray(parm.getString("userList"));  //绑定用户列表
		
		//查询范围
		JSONObject searchCondObj=JSON.parseObject(parm.getString("searchCond"));  //查询范围.
		int searchTypeValue=searchCondObj.getIntValue("searchTypeValue");
		String condValue=searchCondObj.getString("condValue");
		String provinceName=searchCondObj.getString("provinceName");
		String cityName=searchCondObj.getString("cityName");
		String countyName=searchCondObj.getString("countyName");
		
		//确定范围
		List<Map<String,Object>> agentIdList=getAgentScope(searchTypeValue,condValue);
		//根据查询类型、条件值,区域代码条件进行查询		
		List<UserExtends> agentList = userAgentService.searchUserAgent(searchTypeValue, 
				condValue,
				provinceName,
				cityName,
				countyName,
				agentIdList,
				(byte)0);
		
		
		for(UserExtends agent:agentList){
			for(int i=0;i<userArr.size();i++){
				agentBindService.addBindAgentToUser(agent.getExtendId(), 
													 userArr.getJSONObject(i).getLongValue("userId"),
													 userArr.getJSONObject(i).getLongValue("roleId")
													 );
			}
		}
		
		return RequestResultUtil.getResultUpdateSuccess();		
	}
	
	/** 
		* @Title: batchUnbind 
		* @Description: 批量解绑. 
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param model
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/batchunbind")
	@ResponseBody
	public Object batchUnbind(Integer searchTypeValue,String  condValue,
			String  provinceName,String cityName,String	countyName,
			Model model) {
		
		//置默认值
		if(searchTypeValue==null){
			searchTypeValue=0;
			condValue="";
		}
		
		//确定范围
		List<Map<String,Object>> agentIdList=getAgentScope(searchTypeValue,condValue);  
		
		//根据查询类型、条件值,区域代码条件进行查询		
		List<UserExtends> agentList = userAgentService.searchUserAgent(searchTypeValue, 
				condValue,
				provinceName,
				cityName,
				countyName,
				agentIdList,
				(byte)0);
		
		List<Long> deleteAgentIdList=new ArrayList<>();
		for(UserExtends agent:agentList){
			deleteAgentIdList.add(agent.getExtendId());
		}
		
		agentBindService.deleteByAgentId(deleteAgentIdList);  //批量解绑
		
		
		return RequestResultUtil.getResultUpdateSuccess();		
	}
	
	/** 
		* @Title: getAgentScope 
		* @Description: 根据OS用户名称或是IS用户名称来确定agent范围. 
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> getAgentScope(int searchTypeValue,String condValue){
		List<Map<String,Object>> agentIdList=null;  
		if(searchTypeValue==4  && !StringUtil.isEmpty(condValue)){  //OS用户名称
			
			long roleId=getRoleIdByRoleCode(RoleCodeConstants.OS);  //角色ID.
			List<User> userList=userService.getByLikeUserNickname(condValue);  //姓名==条件值,而不是模糊查询
			
			List<Map<String,Object>> tempList=agentBindService.getAgentByUserIdListAndRoleId(userList,roleId);
			agentIdList=new ArrayList<>();
			for(Map<String,Object> bind:tempList){
				Map<String,Object> tempMap=new HashMap<>();
				tempMap.put("cust_id",bind.get("cust_id"));
				agentIdList.add(tempMap);
			}
		}
		
		  
		if(searchTypeValue==5  && !StringUtil.isEmpty(condValue)){  //IS用户名称
			long roleId=getRoleIdByRoleCode(RoleCodeConstants.IS);  //角色ID.
			List<User> userList=userService.getByLikeUserNickname(condValue);  //姓名==条件值,而不是模糊查询
			
			List<Map<String,Object>> tempList=agentBindService.getAgentByUserIdListAndRoleId(userList,roleId);
			agentIdList=new ArrayList<>();
			for(Map<String,Object> bind:tempList){
				Map<String,Object> tempMap=new HashMap<>();
				tempMap.put("cust_id",bind.get("cust_id"));
				agentIdList.add(tempMap);
			}
		}
		return agentIdList;
	}
	
	
	/** 
		* @Title: getRoleIdByRoleCode 
		* @Description: 根据角色CODE获取角色ID 
		* @param @param roleCode
		* @param @return     
		* @return long    返回类型 ,如果查询到则返回roleId,否则返回-1
		* @throws 
	*/
	private long getRoleIdByRoleCode(String roleCode){
		Role rec=new Role();
		rec.setRoleCode(roleCode);
		List<Role> roleList=roleService.select(rec);
		if(roleList.size()>0){
			return roleList.get(0).getRoleId();
		}
		else
			return -1;
	}
	
	
	
	
	/**
	 * @param agentId 签约代理商ID
	 * @return 返回签约代理商OS姓名,如果有多个,以逗号进行分隔
	 */
	private List<Map<String,Object>> getAgentOs(long agentId){
		return agentBindService.getSalesByAgentId(agentId,RoleCodeConstants.OS);
		
	}
	
	/**
	 * @param agentId 签约代理商ID
	 * @return 返回签约代理商IS姓名,如果有多个,以逗号进行分隔
	 */
	private List<Map<String,Object>> getAgentIS(long agentId){
		return agentBindService.getSalesByAgentId(agentId, RoleCodeConstants.IS);		
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
		List<String> parms=new ArrayList<String>();
		parms.add(RoleCodeConstants.OS);
		List<Map<String,Object>> outsideSales=agentBindService.getUsersByRoleCode(parms);
		
		//查询所有IS角色用户
		parms.clear();
		parms.add(RoleCodeConstants.IS);
		List<Map<String,Object>> insideSales=agentBindService.getUsersByRoleCode(parms);
		
		model.addAttribute("outsideSales",outsideSales);
		model.addAttribute("insideSales",insideSales);	
		
		model.addAttribute("agentId",extendId);
	
		return RESPONSE_THYMELEAF_BACK + "agent_bind_osis";
	}
	
	/** 
		* @Title: showBatchBind 
		* @Description: 显示批量绑定界面. 
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/showbatchbind")
	public String showBatchBind(Model model) {
		//查询所有OS角色用户
		List<String> parms=new ArrayList<String>();
		parms.add(RoleCodeConstants.OS);
		List<Map<String,Object>> outsideSales=agentBindService.getUsersByRoleCode(parms);
		
		//查询所有IS角色用户
		parms.clear();
		parms.add(RoleCodeConstants.IS);
		List<Map<String,Object>> insideSales=agentBindService.getUsersByRoleCode(parms);
		
		model.addAttribute("outsideSales",outsideSales);
		model.addAttribute("insideSales",insideSales);	
		
	
		return RESPONSE_THYMELEAF_BACK + "agent_batchbind_osis";
	}
	
	
	
	
	
	/** 
	* @Title: BindSalesToAgent 
	* @Description: 绑定签约客户与OS/IS 
	* @param @param parms 绑定参数,格式json  {"agentId":1,"userList":[{userId:john,roleId:1},{userId:mike,roleId:2}]}
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
		JSONArray userArr=JSON.parseArray(parm.getString("userList"));
		for(int i=0;i<userArr.size();i++){
			this.agentBindService.addBindAgentToUser(agentId, 
													 userArr.getJSONObject(i).getLongValue("userId"),
													 userArr.getJSONObject(i).getLongValue("roleId")
													 );
		}
		
		return RequestResultUtil.getResultUpdateSuccess();		
	}
	
	/** 
		* @Title: agentUnbind 
		* @Description: 解除绑定 
		* @param @param relId 绑定关系ID
		* @param @param model
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/unbind")
	@ResponseBody
	public Object agentUnbind(long relId, Model model) {
		
		CustLockRel rec=new CustLockRel();
		rec.setId(relId);
		rec.setDeleted(DeletedType.YES);
		int row=agentBindService.updateByPrimaryKeySelective(rec);
		if(row>=1)
			return RequestResultUtil.getResultUpdateSuccess();
		else
			return RequestResultUtil.getResultUpdateWarn();
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
