package com.ecp.back.shiro;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.ecp.back.commons.SessionConstants;
import com.ecp.common.util.RsaUtil;
import com.ecp.service.commons.RandomValidateCode;

/**
 * 自定义form认证过滤器
 * 
 * @author srd
 * @date 2017-7-5下午18:30:47
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	//原FormAuthenticationFilter的认证方法
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		//在这里进行验证码的校验
		
		//从session获取正确验证码
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session =httpServletRequest.getSession();
		//取出session的验证码（正确的验证码）
		//String validateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String validateCode = (String) session.getAttribute(RandomValidateCode.RANDOMCODEKEY);
		//String validateCode = (String) session.getAttribute(ValidateCode.RANDOM_CODE_SESSION_KEY);
		
		
		//取出页面的验证码
		//输入的验证和session中的验证进行对比 
		String username = httpServletRequest.getParameter("username");
		String password = httpServletRequest.getParameter("password");
		String randomcode = httpServletRequest.getParameter("kaptcha_code");
		if(randomcode!=null && validateCode!=null && !randomcode.equalsIgnoreCase(validateCode)){
			//如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
			httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
			//拒绝访问，不再校验账号和密码 
			return true; 
		}
		
		return super.onAccessDenied(request, response);
	}

	
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		//return super.createToken(request, response);
		String username = super.getUsername(request);
		String password = super.getPassword(request);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		//自session读取公钥与私钥
		HttpSession session=httpServletRequest.getSession();
		
        String strpk = ((List<String>)session.getAttribute(SessionConstants.KEY_LIST)).get(RsaUtil.PUBLIC_KEY_INDEX);  //公钥        
        String strprivk =((List<String>)session.getAttribute(SessionConstants.KEY_LIST)).get(RsaUtil.PRIVATE_KEY_INDEX) ; //私钥

        X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(strpk.getBytes()));
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strprivk.getBytes()));

        KeyFactory keyf;
        String decryptUsername="";
        String decryptPassword="";
		try {
			keyf = KeyFactory.getInstance("RSA", "BC");
			PublicKey pubKey = keyf.generatePublic(pubX509);
	        PrivateKey privKey = keyf.generatePrivate(priPKCS8);
	        //对接收的数据使用私钥进行解密处理
			decryptUsername= RsaUtil.decryptData(username,privKey);
			decryptPassword=RsaUtil.decryptData(password,privKey);
			
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.createToken(decryptUsername, decryptPassword, request, response);
	}

	/**
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 * 重写 FormAuthenticationFilter 的 onLoginSuccess 方法
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		WebUtils.getAndClearSavedRequest(request);//清除原先的地址
		WebUtils.redirectToSavedRequest(request, response, "/back/main");//设置重定向地址
		//return super.onLoginSuccess(token, subject, request, response);
		return false;
	}

		
}
