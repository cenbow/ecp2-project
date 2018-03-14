package com.ecp.back.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ecp.back.commons.SessionConstants;
import com.ecp.bean.UserBean;
import com.ecp.common.util.DateUtils;

/**
 * Class: SessionTimeoutInterceptor
 * session超时拦截器  
 * @author srd 
 * @version 1.0 $Date: 2016年12月22日 下午2:12:57
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {  
  
	private final Logger log = Logger.getLogger(getClass());
	
    public String[] allowUrls;// 还没发现可以直接配置不拦截的资源，所以在代码里面来排除  
  
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }

	/**
	 * @author: srd $Date: 2016年12月22日
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author: srd $Date: 2016年12月22日
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author: srd $Date: 2016年12月22日
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");  
		log.debug("拦截URL: "+request.getRequestURL());
        if (null != allowUrls && allowUrls.length >= 1)  
            for (String url : allowUrls) {  
                if (requestUrl.contains(url)) {  
                    return true;  
                }  
            }
        HttpSession session = request.getSession();
        log.debug("session创建时间："+DateUtils.getDateTime(session.getCreationTime()));
        log.debug("上次访问时间："+DateUtils.getDateTime(session.getLastAccessedTime()));
        log.debug("session有效时间："+session.getMaxInactiveInterval()+"s");
        
        UserBean user = (UserBean)session.getAttribute(SessionConstants.USER);
        
        if (user != null) {  
        	log.debug("user:"+user);
            return true; // 返回true，则这个方面调用后会接着调用postHandle(), afterCompletion()  
        } else {
        	log.error("user为空");
        	if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with
        		log.error("ajax request timeout !");
	            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
	        }else{
	        	log.error("url request timeout !");
	        	String url = request.getContextPath() + "/goLogin";
	        	response.sendRedirect(url);
	        }  
        	return false;
        }
	}  

	
  
}  
