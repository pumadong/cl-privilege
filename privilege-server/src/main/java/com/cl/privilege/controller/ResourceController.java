package com.cl.privilege.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cl.privilege.api.IPrivilegeBaseApiService;
import com.cl.privilege.biz.IResourceService;
import com.cl.privilege.model.Resource;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.ConfigUtil;
import com.cl.privilege.utils.ConstantUtil;
import com.cl.privilege.utils.JsonUtil;
import com.cl.privilege.utils.SessionUtil;



/**
 *菜单资源管理相关的控制器 
 */

@Controller
@RequestMapping("/controller/resource")
public class ResourceController {

	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping("/list")
    public String main(String visitedModule,String visitedResource,HttpServletRequest request,ModelMap map) {

		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		String menus = privilegeBaseApiService.getModuleTree(user.getId(),visitedModule,visitedResource);
        map.put("user", user);
        map.put("menus", menus);
        
		return "resource/list.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/getResourceTree")
    public String getResourceTree(HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		//这是为了jstree插件使用，这个插件只对Content-Type为json和html的内容进行处理
		response.setContentType("application/json;charset=UTF-8");
	
		return resourceService.getResourceTree(0);
	}
	
	@ResponseBody
	@RequestMapping("/getResourceTreeWithChecked")
    public String getResourceTreeWithChecked(Integer roleId,HttpServletRequest request,HttpServletResponse response,ModelMap map) {

		//这是为了jstree插件使用，这个插件只对Content-Type为json和html的内容进行处理
		response.setContentType("application/json;charset=UTF-8");
	
		return resourceService.getResourceTree(roleId);
	}
	
	@ResponseBody
	@RequestMapping("/get")
    public String get(Integer id,ModelMap map) {
		
		Resource resource = resourceService.getResourceById(id);
		return JsonUtil.convertObj2json(resource).toString();
	}
	
	@ResponseBody
	@RequestMapping("/add")
    public String add(@ModelAttribute("resource")Resource resource,HttpServletRequest request) {
		
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);
		//生成节点
		resourceService.createResource(resource, user);
		return JsonUtil.convertObj2json(resource).toString();
    }
	
	@ResponseBody
	@RequestMapping("/update")
    public String update(@ModelAttribute("resource")  Resource resource,HttpServletRequest request) {
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);

		//生成节点积累
		resourceService.updateResourceById(resource, user);
		
		return JsonUtil.convertObj2json(resource).toString();
    }
	
	@ResponseBody
	@RequestMapping("/delete")
	public String  delete(@RequestParam("id") Integer id) throws Exception{		
	
		//判断节点是否被用户关联
		if(resourceService.isUsedByRole(id))
		{
			return ConstantUtil.Fail;
		}
		
		resourceService.deleteResourceById(id);
		
		return ConstantUtil.Success;
	}
}
