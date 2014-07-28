package com.cl.privilege.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cl.privilege.api.IPrivilegeBaseApiService;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.ConfigUtil;
import com.cl.privilege.utils.SessionUtil;


/**
 * 拦截指定path，进行权限验证，及用户的本地session过期后，重新进行赋值
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

		Assertion assertion=AssertionHolder.getAssertion();
		
		//实际cas-client-core中org.jasig.cas.client.authentication.AuthenticationFilter已经进行了单点登录认证，这里主要是为了获得用户信息
		if(assertion==null 
				|| assertion.getPrincipal()==null
				|| assertion.getPrincipal().getName()==null)
		{
			//没有登录，跳转到没有登录页面
			response.sendRedirect(configUtil.getCasServerUrl());
			return false;
		}

		User user = SessionUtil.getSessionUser(request);
		
		if(user == null)
		{
			//存储Session:用户登录名			
			user = privilegeBaseApiService.getUserByUsername(assertion.getPrincipal().getName());
			request.getSession().setAttribute(SessionUtil.SessionSystemLoginUserName,user);
		}
		
		//判断权限，没有权限，进入没有权限页面
		
		return true;	
		
	}
}
