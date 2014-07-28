package com.cl.privilege.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cl.privilege.api.IPrivilegeBaseApiService;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.ConfigUtil;
import com.cl.privilege.utils.SessionUtil;



/**
 *主界面及登录验证相关的控制器 
 */

@Controller
@RequestMapping("/controller")
public class IndexController {

	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	@Autowired
	private ConfigUtil configUtil;
	
	@RequestMapping("/main")
    public String main(String moduleFlag,HttpServletRequest request,ModelMap map) {

		User user = (User) request.getSession().getAttribute(SessionUtil.SessionSystemLoginUserName);
		String menus = privilegeBaseApiService.getModuleTree(user.getId(),"p","");
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);        
        
        map.put("user", user);
        map.put("menus", menus);
        map.put("hours", hours);
        map.put("moduleFlag", moduleFlag);
        
        return "main.ftl";
    }
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) throws Exception
	{
		SessionUtil.clearSession(request);
		//被拦截器拦截处理
		return "redirect:" + configUtil.getCasServerUrl()+"/logout?service=" + configUtil.getCasServiceUrl();
	}
}
