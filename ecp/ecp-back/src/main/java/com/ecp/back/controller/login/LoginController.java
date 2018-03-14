package com.ecp.back.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecp.back.commons.SessionConstants;
import com.ecp.back.commons.StaticConstants;
import com.ecp.back.commons.TipInfoConstants;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RsaUtil;
import com.ecp.entity.User;
import com.ecp.service.back.IUserService;

@Controller
public class LoginController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private IUserService userService;

	/**
	 * 进入登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goLogin")
	public String backLogin(HttpServletRequest request, HttpServletResponse response, String error) {
		//(1)在此处生成私钥与公钥并置于session中
		List<String> keyList= RsaUtil.createKeyPairs();
		HttpSession session=request.getSession();
		session.setAttribute(SessionConstants.KEY_LIST, keyList);
		//(2)将公钥发送到前台页面
		request.setAttribute("publicKey",keyList.get(RsaUtil.PUBLIC_KEY_INDEX));	
		request.setAttribute("error", error);
		return StaticConstants.LOGIN;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param loginname
	 * @param password
	 * @param remember
	 * @param verify
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> loginSubmit(HttpServletRequest request, HttpServletResponse response, String username,
			String password, String kaptcha) {
		HttpSession session = request.getSession();
		
		log.debug("username : "+username+" , password : "+password);
    	
    	if(StringUtils.isNotBlank(kaptcha)){
    		if(session!=null){
    			String sessionVerifyCode = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
    			if(sessionVerifyCode.equalsIgnoreCase(kaptcha)){
    				return this.verifyUser(request, username, password);
    			}
    		}
    	}
    	log.error(TipInfoConstants.VERIFY_CODE_ERROR_INFO);
    	Map<String, Object> respMap = new HashMap<String, Object>();
    	respMap.put("result_code", "fail");
    	respMap.put("result_err_msg", TipInfoConstants.VERIFY_CODE_ERROR_INFO);
		return respMap;
	}*/
	
	@RequestMapping("/login")
	public String loginSubmit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> respMap = new HashMap<String, Object>();
    	
		String error_msg = null;
		
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME)就是shiro异常类的全限定名
		String exceptionClassName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		//根据shiro返回的异常类路径判断，抛出指定异常信息
		if(exceptionClassName!=null){
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//最终会抛给异常处理器
				System.out.println("账号不存在");
				error_msg = "账号不存在";
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				System.out.println("用户名/密码错误");
				error_msg = "用户名/密码错误";
			} else if("randomCodeError".equals(exceptionClassName)){
				System.out.println("验证码错误");
				error_msg = "验证码错误";
			}else {
				//最终在异常处理器生成未知错误
				System.out.println("未知错误");
				error_msg = "未知错误";
			}
		}
		
		request.setAttribute("error_msg", error_msg);
		
		//(1)在此处生成私钥与公钥并置于session中
		List<String> keyList= RsaUtil.createKeyPairs();
		HttpSession session=request.getSession();
		session.setAttribute(SessionConstants.KEY_LIST, keyList);
		//(2)将公钥发送到前台页面
		request.setAttribute("publicKey",keyList.get(RsaUtil.PUBLIC_KEY_INDEX));
		
		//此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		//登陆失败还到login页面
		//return "redirect:goLogin?error="+error_msg;
		return StaticConstants.LOGIN;
	}
	
	/**
	 * 方法功能：验证用户名和密码
	 * @param request
	 * @param loginname
	 * @param password
	 * @return
	 */
	private Map<String, Object> verifyUser(HttpServletRequest request, String loginname, String password){
		
		Map<String, Object> respMap = new HashMap<String, Object>();
		
    	if(StringUtils.isNotBlank(loginname) && StringUtils.isNotBlank(password)){
    		//user_pass加密规则：UPPER(MD5(CONCAT(user_name,":ECP:",user_pass)))
        	String pass=loginname+":ECP:"+password;
        	log.debug("md5 password upper : "+DigestUtils.md5Hex(pass.getBytes()).toUpperCase());
        	
        	User user = userService.login(loginname, DigestUtils.md5Hex(pass.getBytes()).toUpperCase());
        	if(user!=null){
        		log.debug("用户名为 "+loginname+" 的用户登录成功");
        		HttpSession session = request.getSession();
        		UserBean userBean = createUserBean(user);
        		session.setAttribute(SessionConstants.USER, userBean);
        		//session.setMaxInactiveInterval(10);
        		respMap.put("result_code", "success");
        		respMap.put("result_msg", TipInfoConstants.LOGIN_SUCCESS_INFO);
        		return respMap;
        	}else{
        		log.error("用户名为 "+loginname+" "+TipInfoConstants.LOGIN_FAIL_INFO);
        		respMap.put("result_code", "fail");
        		respMap.put("result_err_msg", TipInfoConstants.LOGIN_FAIL_INFO);
            	return respMap;
        	}
    	}else{
    		log.error(TipInfoConstants.LOGININFO_EMPTY_ERROR_INFO);
    		respMap.put("result_code", "fail");
    		respMap.put("result_err_msg", TipInfoConstants.LOGININFO_EMPTY_ERROR_INFO);
    		return respMap;
    	}
    }
	
	/**
	 * 方法功能：创建用户bean
	 * @param user
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年5月7日 上午15:25:39</p>
	 */
	private UserBean createUserBean(User user){
		return new UserBean(user.getId(), user.getAuditRemark(), user.getAuditor(), user.getCreatedTime(), user.getDeleted(), user.getDepartment(), user.getEmail(), user.getGrowthValue(), user.getLinkMan(), user.getLinkPhoneNum(), user.getMobile(), user.getNickname(), user.getOldpassword(), user.getParentId(), user.getPassword(), user.getQuickType(), user.getSecurityLevel(), user.getStatus(), user.getType(), user.getUpdateTime(), user.getUsername());
	}
	
	/**
	 * 用户退出登录
	 * 
	 * @param request
	 * @return
	 */
	/*@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();// session 无效
		return "redirect:/login";
	}*/

}
