package com.ecp.back.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.AccountStatusType;
import com.ecp.bean.DeletedType;
import com.ecp.bean.UserBean;
import com.ecp.bean.UserType;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.common.util.RequestResultUtil;
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

/**
 * @ClassName UserAgentController
 * @Description 签约客户维护控制器
 * @author Administrator
 * @Date 2017年6月17日 上午11:37:13
 * @version 1.0.0
 */
/**
	* Copyright (c) 2017 by Hz
	* @ClassName:     MyAgentController.java
	* @Description:   我的:签约客户维护控制器 (OS/IS使用)
	* 
	* @author:        lenovo
	* @version:       V1.0  
	* @Date:          2018年4月13日 上午10:28:51 
*/
@Controller
@RequestMapping("/back/myagent")
public class MyAgentController {
	final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/myagent/";
	final String RESPONSE_JSP = "jsps/front/";
	
	final String DEFAULT_PASSWORD="123456";

	private final int PAGE_SIZE = 8;

	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	IUserAgentService userAgentService;  //代理商
	@Autowired
	IUserService userService;  //用户
	@Autowired
	IAgentService agentService; //用户(代理商下所分配的帐号):
	@Autowired
	IAgentBindService agentBindService;  //客户绑定服务
	@Autowired
	IRoleService roleService;  //角色服务

	/**
	 * @Description 显示-签约客户外围框架
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String user_agent_show(Model model) {
		return RESPONSE_THYMELEAF_BACK + "user_agent_show";
	}
	
	/**
	 * @Description  显示签约代理商列表
	 * @param pageNum 查询页号
	 * @param pageSize 页大小
	 * @param searchTypeValue 查询字段（以整形表示：0-选择查询条件；1：企业名称；2：负责人姓名；3：电话号码）
	 * @param condValue 查询条件值
	 * @return 
	 */
	@RequestMapping(value = "/agenttable")
	public String user_agent_agenttable(Integer pageNum, 
										Integer pageSize,
										Integer searchTypeValue,
										String condValue,
										String provinceName,
										String cityName,
										String countyName,
										long userId,
										long roleId,
										byte auditStatus,
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
		
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);  //确认登录用户所查询的代理商范围
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper
		//根据查询类型、条件值进行查询
		//List<UserExtends> userAgents = userAgentService.getAllUserAgent();
		//List<UserExtends> userAgents = userAgentService.searchUserAgent(searchTypeValue, "%"+condValue+"%");
		List<UserExtends> userAgents = userAgentService.searchUserAgent(searchTypeValue, 
																		condValue,
																		provinceName,
																		cityName,
																		countyName,
																		agentIdList,
																		auditStatus);
		
		PageInfo<UserExtends> pageInfo = new PageInfo<>(userAgents);// (使用了拦截器或是AOP进行查询的再次处理)
		
		
		List<Map<String,Object>> userRoleList=getUserRoles();		//查询用户角色列表
		model.addAttribute("userRoleList", userRoleList);  
		
		
		model.addAttribute("pageInfo", pageInfo);  					//分页信息
		model.addAttribute("searchTypeValue", searchTypeValue);  	//查询字段值
		model.addAttribute("condValue", condValue);  				//查询条件值
		model.addAttribute("userAgents", userAgents);				//代理商列表
		
		//回传区域条件
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("countyName", countyName);
		
		//用户/角色条件
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
		
		//审核条件
		model.addAttribute("auditStatus",auditStatus);
		
		

		return RESPONSE_THYMELEAF_BACK + "user_agent_table";
	}
	
	
	/** 
	* @Title: getSearchScope 
	* @Description: 获取指定用户的查询范围 
	* @param @param userId
	* @param @param roleId
	* @param @return  如果返回null,则为查询所有   
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	private List<Map<String,Object>> getSearchScope(long userId,long roleId){
		//确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=null;
		if(userId!=0 && roleId!=0)  //选择了某个用户
			agentIdList=agentBindService.getAgentIdListByBindedUser(userId,roleId);
		else{
			//(1)根据登录用户的角色判定是否查询所有.如果是ADMIN/经理,则查询所有
			boolean searchAll=needSearchAll();
			if(!searchAll){  //非admin用户
				long loginUserId=getLoginUserId();
				agentIdList=agentBindService.getAgentIdListByBindedUser(loginUserId);
			}			
		}
		return agentIdList;
	}
	
	/** 
	* @Title: getLoginUserId 
	* @Description: 获取登录用户的ID 
	* @param @return     
	* @return long    返回类型 
	* @throws 
	*/
	private long getLoginUserId(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		return user.getId();
	}


	/** 
	* @Title: needSearchAll 
	* @Description: 根据登录用户的角色:是否可查询所有代理商订单
	* @param @return
	* 				如果是经理级别,则可查询所有;
	* 				如果OS/IS则只可查询与自己所绑定代理商范围内的     
	* @return boolean    返回类型 
	* @throws 
	*/
	private boolean needSearchAll(){
	//取得当前用户角色列表
	Subject subject = SecurityUtils.getSubject();
	UserBean user = (UserBean)subject.getPrincipal();
	List<Role> roleList=user.getRoleList();
	
	//查询是否为经理级别
	for(int i=0;i<roleList.size();i++){
		Role role=roleList.get(i);
		if(role.getRoleCode()==null || role.getRoleCode().equals("")){
			continue;
		}
		switch(role.getRoleCode()){
		case RoleCodeConstants.ADMIN:
		case RoleCodeConstants.MANAGER:
		case RoleCodeConstants.BUSSMAN:
		case RoleCodeConstants.SALEMAN:				
			return true;
		default:
			break;				
		}
	}
	
	//查询是否为OS/IS级别
	//查询是否为经理级别
	for(int i=0;i<roleList.size();i++){
		Role role=roleList.get(i);
		if(role.getRoleCode()==null || role.getRoleCode().equals("")){
			continue;
		}
		
		switch(role.getRoleCode()){
		case RoleCodeConstants.OS:
		case RoleCodeConstants.IS:
		{
			return false;
		}
		default:
			break;
		}
	}
	
	return false;	
	
	}


	//
	/** 
		* @Title: getUserRoles 
		* @Description: 查询当前登录用户所有角色.
		* 				如果是OS/IS登录,则只是自己的角色及用户名称(可能有多个,如某人可能即是OS也是IS);
		* 				如果是经理级别则显示所有的OS/IS用户名及角色名 
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>>  getUserRoles(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		List<Role> roleList=user.getRoleList();
		
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			switch(role.getRoleCode()){
			case RoleCodeConstants.ADMIN:case RoleCodeConstants.MANAGER:
			case RoleCodeConstants.BUSSMAN:	case RoleCodeConstants.SALEMAN:	
				//查询所有的OS/IS列表
				List<String> parms= new ArrayList<String>();
				parms.add(RoleCodeConstants.OS);
				parms.add(RoleCodeConstants.IS);
				return agentBindService.getUsersByRoleCode(parms);
			default:
				break;				
			}
		}
		
		//查询是否为OS/IS级别
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			
			switch(role.getRoleCode()){
			case RoleCodeConstants.OS:case RoleCodeConstants.IS:
			{
				//查询此用户所对应的OS/IS角色				
				List<String> parms= new ArrayList<String>();
				parms.add(RoleCodeConstants.OS);
				parms.add(RoleCodeConstants.IS);
				return agentBindService.getUsersByUserIdAndRoleCode(user.getId(), parms);
			}
			default:
				break;
			}
		}
		
		return new ArrayList<Map<String,Object>>();
		
	}
	
	
	
	/** 
		* @Title: agent_userTable 
		* @Description: 返回此代理商下的用户列表 
		* @param @param agentId 代理商ID
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/usertable")
	public String agent_userTable(long agentId,Model model) {
		getUsersByAgentId(agentId,model);
		return RESPONSE_THYMELEAF_BACK + "agent_usertable";
	}
	
	
	@RequestMapping(value = "/showusertable")
	public String showAgentUserTable(long agentId,Model model) {
		getUsersByAgentId(agentId,model);
		return RESPONSE_THYMELEAF_BACK + "agent_usertable_show";
	}
	
	private void getUsersByAgentId(long agentId,Model model){
		List<Map<String,Object>> userList=userAgentService.getUsersByAgentId(agentId);
		model.addAttribute("userList", userList);
		model.addAttribute("agentId", agentId);   //回传参数
	}
	
	
	/**
	 * @Description 增加签约客户 导航至增加签约客户界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String user_agent_add(Model model) {
		return RESPONSE_THYMELEAF_BACK + "user_agent_add";
	}
	
	
	/**
	 * @Description 分配帐号（帐号默认为有效状态）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dispatch" ,method=RequestMethod.POST)
	@ResponseBody
	public Object user_agent_dispatch(HttpServletRequest request){
		
		String loginName=request.getParameter("loginName");
		String nickName=request.getParameter("nickName");
		String password=request.getParameter("password");
		long agentId=Long.parseLong(request.getParameter("agentId"));
		
		
		//(1)生成user对象,并设置其属性
		User user=new User();
		user.setCreatedTime(new Date());
		user.setUsername(loginName);
		user.setNickname(nickName);
		String md5Pass=genMD5Password(loginName,password);
		user.setPassword(md5Pass);
		user.setStatus(AccountStatusType.VALID);
		user.setType(UserType.AGENT);  //帐号类型
		user.setDeleted(DeletedType.NO);//删除类型：1=未删除，2=已删除
		
		//(2)判定是否是第一个用户,如果是则为主帐号
		UserExtends agent=userAgentService.selectByPrimaryKey(agentId);  //获取代理商对象
		if(agent.getUserId()==null || agent.getUserId()==0){  //主帐号
			
			user.setParentId((long)0);  //设置主帐号的parentId为0,主帐号为root node
			int row =userService.insertSelective(user);   //向user中增加记录
			
			if(row>0){
				UserExtends tempAgent=new UserExtends();
				tempAgent.setExtendId(agentId);
				tempAgent.setUserId(user.getId());
				tempAgent.setAccountState(AccountStatusType.VALID);
				userAgentService.updateByPrimaryKeySelective(tempAgent);
			}
		}
		else{  //子帐号
			long primaryUserId=agent.getUserId();  //主帐号ID
			user.setParentId(primaryUserId);
			int row =userService.insertSelective(user);   //向user中增加记录
		}
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	/**
	 * @Description 分配帐号（帐号默认为有效状态）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modiuser" ,method=RequestMethod.POST)
	@ResponseBody
	public Object modiAgentUser(HttpServletRequest request){
		
		String loginName=request.getParameter("loginName");
		String nickName=request.getParameter("nickName");
		String password=request.getParameter("password");
		long agentId=Long.parseLong(request.getParameter("agentId"));
		long userId=Long.parseLong(request.getParameter("userId"));
		
		
		//(1)生成user对象,并设置其属性
		User user=new User();
		user.setId(userId);		
		user.setUsername(loginName);
		user.setNickname(nickName);
		if(!(password==null || password.equals(""))){  //如果口令字段不为空时则置新的口令,否则不处理
			String md5Pass=genMD5Password(loginName,password);
			user.setPassword(md5Pass);
		}
		
		int row=userService.updateByPrimaryKeySelective(user);  //更新用户
		if (row>=1) 
			return RequestResultUtil.getResultUpdateSuccess();
		else
			return RequestResultUtil.getResultUpdateWarn();
	}
	
	
	/** 
		* @Title: deleteAgentUser 
		* @Description: 删除用户 
		* @param @param request
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/deluser" ,method=RequestMethod.POST)
	@ResponseBody
	public Object deleteAgentUser(HttpServletRequest request){
		
		long agentId=Long.parseLong(request.getParameter("agentId"));
		long userId=Long.parseLong(request.getParameter("userId"));
		
		
		UserExtends agent=userAgentService.selectByPrimaryKey(agentId);  //获取代理商对象
		if(userId==agent.getUserId()){  //如果删除:主帐号
			
			//(1)更新主帐号为删除状态
			User primaryUser=new User();
			primaryUser.setId(userId);
			primaryUser.setDeleted(DeletedType.YES);
			userService.updateByPrimaryKeySelective(primaryUser);  
			
			//(2)更新子帐号为删除状态
			User subUser=new User();
			subUser.setParentId(userId);
			userService.updateByPrimaryKeySelective(subUser);
			
			//(3)更新代理商中主帐号为空
			UserExtends tempAgent=new UserExtends();
			tempAgent.setExtendId(agentId);
			tempAgent.setUserId((long)0);
			//tempAgent.setAccountState(AccountStatusType.INVALID);  //如果删除了主帐号,则代理商自动置为无效状态
			userAgentService.updateByPrimaryKeySelective(tempAgent);
			
		}
		else{  //删除子帐号
			User user=new User();
			user.setId(userId);
			user.setDeleted(DeletedType.YES);
			userService.updateByPrimaryKeySelective(user);  //更新用户
		}
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	
	/**
	 * @Description 重置口令 (ajax请求)	 
	 * @param userId 用户ID 代理商所分配的用户ID
	 * @return 返回相应的MAP.(成功/警告)
	 */
	@RequestMapping(value="/reset_password")
	@ResponseBody
	public Object user_agent_reset_password(long userId){
		
		//(1)读取原来的登录用户名称
		User user=userService.selectByPrimaryKey(userId);
		String loginName=user.getUsername();
		//(2)计算默认口令的加密结果
		String password=genMD5Password(loginName,DEFAULT_PASSWORD);
		user.setPassword(password);
		//(3)采用默认口令进行更新
		int row=userService.updateByPrimaryKeySelective(user);
		if (row==1){			
			return RequestResultUtil.getResultUpdateWarn("此用户口令已经重置.用户登录名称:"+loginName+";口令:123456");
		}
		else
			return RequestResultUtil.getResultUpdateWarn();
		
	}
	
	
	/**
	 * @Description 查询是否有相同的登录帐号
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sameloginname" ,method=RequestMethod.POST)
	@ResponseBody
	public Object searchSameLoginName(String loginName,HttpServletRequest request){
		User user=new User();
		user.setUsername(loginName);
		
		boolean has=agentService.hasSameLoginName(loginName);
		if(has)
			return RequestResultUtil.getResultSelectSuccess();
		
		
		return RequestResultUtil.getResultSelectWarn();
	}
	
	
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
		* @Title: setAgentState 
		* @Description: 设置代理商状态:代理商是否有效. 
		* @param @param agentId
		* @param @param state
		* @param @param request
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/setstate" ,method=RequestMethod.POST)
	@ResponseBody
	public Object setAgentState(long agentId,int state, HttpServletRequest request){
		UserExtends agent=new UserExtends();
		agent.setExtendId(agentId);
		agent.setAccountState(state);
		userAgentService.updateByPrimaryKeySelective(agent);
		
		//设置主帐号为无效状态
		/*User user=new User();
		user.setId(userId);
		user.setStatus(accountState);
		userService.updateByPrimaryKeySelective(user);*/
		
		return RequestResultUtil.getResultUpdateSuccess(); 
	}
	
	/** 
		* @Title: setUserState 
		* @Description: 设置用户的状态 
		* @param @param userId
		* @param @param status
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/setuserstatus" ,method=RequestMethod.POST)
	@ResponseBody
	public Object setUserState(long userId,int status){
		
		//设置主帐号为无效状态
		User user=new User();
		user.setId(userId);
		user.setStatus(status);
		userService.updateByPrimaryKeySelective(user);
		
		return RequestResultUtil.getResultUpdateSuccess(); 
	}
	
	/** 
		* @Title: setPrimaryUser 
		* @Description: 设置指定的用户为主帐号 
		* @param @param userId
		* @param @param agentId
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/setprimaryuser" ,method=RequestMethod.POST)
	@ResponseBody
	public Object setPrimaryUser(long userId,long agentId){
		
		User newPrimaryUser=userService.selectByPrimaryKey(userId);
		
		UserExtends agent=userAgentService.selectByPrimaryKey(agentId);
		
		long oldPrimaryUserId=agent.getUserId();
		User oldPrimaryUser=userService.selectByPrimaryKey(oldPrimaryUserId);
		
		//(1)更新所有子帐号的parentId
		User rec=new User();
		rec.setParentId(oldPrimaryUserId);
		List<User> subUserList=userService.select(rec);
		for(User subUser:subUserList){
			subUser.setParentId(userId);
			userService.updateByPrimaryKeySelective(subUser);
		}
		
		//(2)设置原来的主帐号为子帐号
		oldPrimaryUser.setParentId(userId);
		userService.updateByPrimaryKeySelective(oldPrimaryUser);
		
		//(3)设置新的主帐号
		newPrimaryUser.setParentId((long)0);
		userService.updateByPrimaryKeySelective(newPrimaryUser);
		
		//(4)设置代理商主帐号
		agent.setUserId(userId);
		userAgentService.updateByPrimaryKeySelective(agent);
		
		
		return RequestResultUtil.getResultUpdateSuccess(); 
	}
	
	
	/**
	 * @Description 插入签约客户
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
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
	}

	/**
	 * @Description 更新签约客户
	 * @param agent 签约客户对象
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object user_agent_update(UserExtends agent,HttpServletRequest request) {
		//System.out.println(agent.getBusinessLicencePicSrc());
		//处理上传文件
		if (!this.processUploadFile(request, agent)) {
			return RequestResultUtil.getResultUploadWarn();
		}

		int row=userAgentService.updateByPrimaryKeySelective(agent);
		if (row > 0) {
			return RequestResultUtil.getResultUpdateSuccess();
		}

		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * @Description 文件上传
	 * @param request
	 * @param brand
	 * @return
	 */
	private boolean processUploadFile(HttpServletRequest request, UserExtends agent) {
		boolean flag = false;
		try {
			//上传营业执照
			String backImgPath = FileUploadUtil.getFile2Upload(request, "back logo", "businessLicencePic_Src");
			if (StringUtils.isNotBlank(backImgPath)) {
				if (!FileUploadUtil.deleteFile(request, agent.getBusinessLicencePicSrc())) {
					log.error("文件不存在或已删除 logo图路径：" + agent.getBusinessLicencePicSrc());
				}
				agent.setBusinessLicencePicSrc(backImgPath);
			}

			//上传税务登录证
			backImgPath = FileUploadUtil.getFile2Upload(request, "back logo", "taxRegistrationCertificatePic_Src");
			if (StringUtils.isNotBlank(backImgPath)) {
				if (!FileUploadUtil.deleteFile(request, agent.getTaxRegistrationCertificatePicSrc())) {
					log.error("文件不存在或已删除 logo图路径：" + agent.getTaxRegistrationCertificatePicSrc());
				}
				agent.setTaxRegistrationCertificatePicSrc(backImgPath);
			}

			//上传组织机构代码证
			backImgPath = FileUploadUtil.getFile2Upload(request, "back logo", "organizationPic_Src");
			if (StringUtils.isNotBlank(backImgPath)) {
				if (!FileUploadUtil.deleteFile(request, agent.getOrganizationPicSrc())) {
					log.error("文件不存在或已删除 logo图路径：" + agent.getOrganizationPicSrc());
				}
				agent.setOrganizationPicSrc(backImgPath);
			}

			flag = true;
		} catch (IOException e) {
			log.error("上传文件异常", e);
		} catch (Exception e) {
			log.error("删除上传文件异常", e);
		}
		return flag;
	}

	/**
	 * @Description 根据用户名及口令生成MD5加密口令
	 * @param loginName
	 * @param password
	 * @return 生成MD5的加密口令
	 */
	private String genMD5Password(String loginName, String password) {
		// user_pass加密规则：UPPER(MD5(CONCAT(user_name,":CNWELL:",user_pass)))
		String pass = loginName + ":CNWELL:" + password;
		// log.debug("md5 password upper : " +
		// DigestUtils.md5Hex(pass.getBytes()).toUpperCase());
		String md5Password = DigestUtils.md5Hex(pass.getBytes()).toUpperCase();
		return md5Password;
	}

}
