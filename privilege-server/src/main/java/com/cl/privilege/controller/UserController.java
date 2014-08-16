package com.cl.privilege.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cl.privilege.api.IPrivilegeBaseApiService;
import com.cl.privilege.biz.IDepartmentService;
import com.cl.privilege.biz.IRoleService;
import com.cl.privilege.biz.IUserService;
import com.cl.privilege.model.Department;
import com.cl.privilege.model.User;
import com.cl.privilege.model.UserSearchModel;
import com.cl.privilege.utils.ConfigUtil;
import com.cl.privilege.utils.ConstantUtil;
import com.cl.privilege.utils.JsonUtil;
import com.cl.privilege.utils.SessionUtil;
import com.cl.privilege.utils.StringUtil;



/**
 *用户管理相关的控制器 
 */

@Controller
@RequestMapping("/controller/user")
public class UserController {

	@Autowired
	private IPrivilegeBaseApiService privilegeBaseApiService;
	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/list")
    public String main(String visitedModule,String visitedResource,HttpServletRequest request,ModelMap map) {

		//初始化用户、菜单
		User user = SessionUtil.getSessionUser(request);
		String menus = privilegeBaseApiService.getModuleTree(user.getId(),visitedModule,visitedResource);
        map.put("user", user);
        map.put("menus", menus);
        
		return "user/list.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/getUserDataTables")
    public String getUserDataTables(UserSearchModel searchModel,ModelMap map) {
		return userService.getUserDataTables(searchModel);
	}
	
	@ResponseBody
	@RequestMapping("/getUserDataRow")
	public String  getUserDataRow(@RequestParam("id") Integer id) throws Exception{		
		return userService.getUserDataRow(id);
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String  get(@RequestParam("id") Integer id) throws Exception{		
		User user = userService.getUserById(id);		
		return JsonUtil.convertObj2json(user).toString();	
	}
	
	@RequestMapping("/addform")
    public String addform(ModelMap map) {
		List<Department> departments = departmentService.getDepartmentListForOption();
		map.put("departments", departments);
		return "user/addform.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/add")
    public String add(@ModelAttribute("user")User user,HttpServletRequest request) {
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);		
		userService.createUser(user, operator);
		return ConstantUtil.Success;
    }
	
	@RequestMapping("/updateform")
    public String updateform(Integer id,HttpServletRequest request,ModelMap map) {
		List<Department> departments = departmentService.getDepartmentListForOption();		
		User user = userService.getUserById(id);
		map.put("departments", departments);
		map.put("user", user);
		return "user/updateform.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/update")
    public String update(@ModelAttribute("user")  User user,HttpServletRequest request) {
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);
		
		userService.updateUserById(user,operator);
		
		return ConstantUtil.Success;
    }
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") Integer id,HttpServletRequest request){
		
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);
				
		User user = new User();
		user.setId(id);
		user.setIsDelete(true);
	
		userService.updateUserById(user, operator);
		
		return ConstantUtil.Success;
	}
	
	@ResponseBody
	@RequestMapping("/resetpass")
	public String resetpass(@RequestParam("id") Integer id,HttpServletRequest request){		
	
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);
				
		User user = new User();
		user.setId(id);
		user.setPassword(ConstantUtil.DefaultMd5Password);
		
		userService.updateUserById(user, operator);
		
		return ConstantUtil.Success;
	}
	
	@ResponseBody
	@RequestMapping("/lock")
	public String lock(@RequestParam("id") Integer id,HttpServletRequest request){		
	
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);
				
		User user = new User();
		user.setId(id);
		user.setIsLock(true);
		
		userService.updateUserById(user, operator);
		
		return ConstantUtil.Success;
	}
	
	@ResponseBody
	@RequestMapping("/unlock")
	public String unlock(@RequestParam("id") Integer id,HttpServletRequest request){		
	
		//从session取出User对象
		User operator = SessionUtil.getSessionUser(request);
				
		User user = new User();
		user.setId(id);
		user.setIsLock(false);
		
		userService.updateUserById(user, operator);
		
		return ConstantUtil.Success;
	}
	
	@RequestMapping("/assignform")
    public String assignform(Integer id,ModelMap map) {
		
		map.put("options", roleService.getRoleForOptions(id));
		map.put("id", id);
		
		return "user/assignform.ftl";
    }
	
	@ResponseBody
	@RequestMapping("/assign")
	public String assign(Integer id,String selectedStr)
	{
		if(id==null || StringUtil.isStrEmpty(id.toString()) || StringUtil.isStrEmpty(selectedStr))
		{
			return ConstantUtil.Fail;
		}
		userService.assignRole(id, selectedStr);
		
		return ConstantUtil.Success;
	}
}
