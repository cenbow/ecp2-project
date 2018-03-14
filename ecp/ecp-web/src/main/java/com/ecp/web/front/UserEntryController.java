package com.ecp.web.front;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ecp.common.RsaUtil;
import com.ecp.common.SessionConstants;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.User;
import com.ecp.service.front.IAgentService;

/**
 * @ClassName AgentEntryController
 * @Description 帐户-注册、登录 控制器
 * @author Administrator
 * @Date 2017年5月18日 上午6:32:40
 * @version 1.0.0
 */
@Controller
@RequestMapping("/login/agent")
public class UserEntryController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	//private final Logger log = Logger.getLogger(getClass());
	
	final String LOGIN_STATE_YES="login";
	final String LOGIN_STATE_NO="notlogin";
	
	

	@Autowired
	IAgentService agentService;

	/**
	 * @Description 帐户-注册
	 * @param loginName  登录名称
	 * @param password   口令
	 * @return 如果成功注册，则导航到主页，如果输入信息错误则提示用户
	 */
	// TODO 在此处只处理成功注册 后面再处理异常情况
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String loginName, String password) {
		String md5Password = genMD5Password(loginName, password);
		agentService.register(loginName, md5Password);
		return "redirect:/front/home/home";  //导航到主页
	}

	/**
	 * @Description 帐户-登录（普通方式）
	 * @param request
	 * @param loginName
	 * @param password
	 * @return
	
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, String loginName, String password)  {
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		//自session读取公钥与私钥
		HttpSession session=request.getSession();
		
        String strpk = ((List<String>)session.getAttribute(SessionConstants.KEY_LIST)).get(RsaUtil.PUBLIC_KEY_INDEX);  //公钥        
        String strprivk =((List<String>)session.getAttribute(SessionConstants.KEY_LIST)).get(RsaUtil.PRIVATE_KEY_INDEX) ; //私钥

        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(strpk.getBytes()));
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strprivk.getBytes()));

        KeyFactory keyf;
        String decryptLoginName="";
        String decryptPassword="";
		try {
			keyf = KeyFactory.getInstance("RSA", "BC");
			PublicKey pubKey = keyf.generatePublic(pubX509);
	        PrivateKey privKey = keyf.generatePrivate(priPKCS8);
	        //对接收的数据使用私钥进行解密处理
			decryptLoginName= RsaUtil.decryptData(loginName,privKey);
			decryptPassword=RsaUtil.decryptData(password,privKey);
			
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		User user = verifyUser(decryptLoginName, decryptPassword);
		if (user!=null) { // 如果校难正确
			
			//将用户信息加入到session
			session = request.getSession();    		
    		session.setAttribute(SessionConstants.USER, user);
    		session.removeAttribute(SessionConstants.KEY_LIST);
    		
			//return "redirect:/front/home/home"; // 导航到主页
    		return RequestResultUtil.getResultSelectSuccess();
		} else {
			return RequestResultUtil.getResultSelectWarn();
		}

	}
	
	
	
	
	/**
	 * @Description 导航->登录页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/gologin")
	public String login(HttpServletRequest request,Model model) {
		//(1)在此处生成私钥与公钥并置于session中
		List<String> keyList= RsaUtil.createKeyPairs();
		HttpSession session=request.getSession();
		session.setAttribute(SessionConstants.KEY_LIST, keyList);
		//(2)将公钥发送到前台页面
		model.addAttribute("publicKey",keyList.get(RsaUtil.PUBLIC_KEY_INDEX));	
		
		return RESPONSE_THYMELEAF + "login";
	}
	
	/**
	 * @Description 修改口令
	 * @param loginName  登录名
	 * @param oldPassword 原口令
	 * @param newPassword 新口令
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updatepassword")
	@ResponseBody
	public Object change_password(String loginName,String oldPassword,String newPassword,Model model) {
		User user = verifyUser(loginName, oldPassword);
		if (user!=null) { // 如果校难正确
			
			user.setPassword(genMD5Password(loginName,newPassword));
			agentService.updateByPrimaryKeySelective(user);
    		
    		return RequestResultUtil.getResultUpdateSuccess();
		} else {
			return RequestResultUtil.getResultUpdateWarn();
		}

		
	}
	
	
	
	/**
	 * 
	 * @Description 用户登录-AJAX方式
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxLogin(HttpServletRequest request, Model model) {
		String loginState=LOGIN_STATE_NO;
		
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		
		/*System.out.println("param1 is:" + loginName);
		System.out.println("param2 is:" + password);*/
		
		
		User user = verifyUser(loginName, password);
		if (user!=null) { // 如果校难正确
			//将用户信息加入到session
			HttpSession session = request.getSession();    		
    		session.setAttribute(SessionConstants.USER, user);
			loginState=LOGIN_STATE_YES;
		} else {
			loginState=LOGIN_STATE_NO;
		}
				

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", loginState);
		jsonObject.put("status", "success");

		return jsonObject;
	}
	
	
	@RequestMapping(value = "/loginstate", method = RequestMethod.POST)
	@ResponseBody
	public Object getLoginState(HttpServletRequest request, Model model) {		
		
		String loginState="";
		HttpSession session = request.getSession();    		
		User user =(User)session.getAttribute(SessionConstants.USER);
		
		if(user==null){  //如果用户没有登录
			loginState=LOGIN_STATE_NO;
		}
		else{  //已经登录，将sku加入到购物车中
			loginState=LOGIN_STATE_YES;
		}		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", loginState);
		jsonObject.put("status", "success");
		
		

		return jsonObject;
	} 
	
	

	/**
	 * @Description 验证用户名和密码
	 * @param loginName
	 * @param password
	 * @return User:如果验证成功  null:验证不成功
	 */
	private User verifyUser(String loginName, String password) {
		
		if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(password)) {
			String md5Password = genMD5Password(loginName, password);
			User user = agentService.login(loginName, md5Password);
			if (user != null) {
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}		
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
	
	public static void main(String args[]) { 
		String pass = "jch" + ":CNWELL:" + "12345";
		// log.debug("md5 password upper : " +
		// DigestUtils.md5Hex(pass.getBytes()).toUpperCase());
		String md5Password = DigestUtils.md5Hex(pass.getBytes()).toUpperCase();
		System.out.println(md5Password);
	}

}
