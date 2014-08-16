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
import com.cl.privilege.biz.IDepartmentService;
import com.cl.privilege.model.Department;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.ConfigUtil;
import com.cl.privilege.utils.ConstantUtil;
import com.cl.privilege.utils.JsonUtil;
import com.cl.privilege.utils.SessionUtil;



/**
 *部门管理相关的控制器 
 */

@Controller
@RequestMapping("/controller/department")
public class DepartmentController {

	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/list")
    public String main(String visitedModule,String visitedResource,HttpServletRequest request,ModelMap map) {

		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		String menus = privilegeBaseApiService.getModuleTree(user.getId(),visitedModule,visitedResource);
        map.put("user", user);
        map.put("menus", menus);
        
		return "department/list.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/getDepartmentTree")
    public String getDepartmentTree(HttpServletResponse response,ModelMap map) {

		//这是为了jstree插件使用，这个插件只对Content-Type为json和html的内容进行处理		
		response.setContentType("application/json;charset=UTF-8");
	
		return departmentService.getDepartmentTree();
	}
	
	@ResponseBody
	@RequestMapping("/get")
    public String get(Integer id,ModelMap map) {
		
		Department department = departmentService.getDepartmentById(id);		
		return JsonUtil.convertObj2json(department).toString();
	}
	
	@ResponseBody
	@RequestMapping("/add")
    public String add(@ModelAttribute("department")Department department,HttpServletRequest request) {
		
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);
		//生成节点
		departmentService.createDepartment(department,user);		
		return JsonUtil.convertObj2json(department).toString();
    }
	
	@ResponseBody
	@RequestMapping("/update")
    public String update(@ModelAttribute("department")  Department department,HttpServletRequest request) {
		//从session取出User对象
		User user = SessionUtil.getSessionUser(request);

		//生成节点积累
		departmentService.updateDepartmentById(department,user);
		
		return JsonUtil.convertObj2json(department).toString();
    }
	
	@ResponseBody
	@RequestMapping("/delete")
	public String  delete(@RequestParam("id") Integer id) throws Exception{		
	
		//判断节点是否被用户关联
		if(departmentService.isUsedByUser(id))
		{
			return ConstantUtil.Fail;
		}
		
		departmentService.deleteDepartmentById(id);
		
		return ConstantUtil.Success;
	}
}
