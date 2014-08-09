package com.cl.privilege.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cl.privilege.biz.IRoleService;
import com.cl.privilege.mapper.RoleMapper;
import com.cl.privilege.model.Role;
import com.cl.privilege.model.RoleSearchModel;
import com.cl.privilege.model.User;
import com.cl.privilege.utils.ConstantUtil;
import com.cl.privilege.utils.StringUtil;


@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	private void setPersonInsert(Role role,User user)
	{
		Date d = new Date();
		role.setCreatePerson(user.getUsername());
		role.setUpdatePerson(user.getUsername());
		role.setCreateDate(d);
		role.setUpdateDate(d);
	}
	private void setPersonUpdate(Role role,User user)
	{
		Date d = new Date();
		role.setUpdatePerson(user.getUsername());
		role.setUpdateDate(d);
	}

	@Override
	public Role getRoleById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer createRole(Role role,User user) {
		setPersonInsert(role,user);
		return roleMapper.insertSelective(role);
	}

	@Override
	public Integer updateRoleById(Role role,User user) {
		setPersonUpdate(role,user);
		return roleMapper.updateByPrimaryKeySelective(role);
	}
	
	@Override
	public Integer deleteRoleById(Integer id)
	{
		return roleMapper.deleteRoleById(id);
	}
	
	@Override
	public Integer getRoleTotalBySearch(RoleSearchModel searchModel)
	{
		return roleMapper.getRoleTotalBySearch(searchModel);
	}

	@Override
	public List<Role> getRoleListBySearch(RoleSearchModel searchModel)
	{
		return roleMapper.getRoleListBySearch(searchModel, 
				new RowBounds((searchModel.getPageNo() - 1) * searchModel.getPageSize(), searchModel.getPageSize()));
	}
	
	@Override
	public Boolean isUsedByUser(Integer roleId)
	{
		return roleMapper.isUsedByUser(roleId);
	}
	
	@Override
	@Transactional
	public String assignModuleAndResource(Integer roleId,String checkedStr)
	{
		String[] checkedArr = checkedStr.split(",");
		List<Integer> moduleIds = new ArrayList<Integer>();
		List<Integer> resourceIds = new ArrayList<Integer>();
		for(String s:checkedArr)
		{
			if(s.startsWith("m_"))
			{
				moduleIds.add(Integer.parseInt(s.replace("m_", "")));
			} else {
				resourceIds.add(Integer.parseInt(s));
			}
		}
		
		roleMapper.deleteRoleModuleById(roleId);
		if(moduleIds.size()>0)
		{			
			roleMapper.assignModules(moduleIds, roleId);
		}
		
		roleMapper.deleteRoleResourceById(roleId);
		if(resourceIds.size()>0)
		{			
			roleMapper.assignResources(resourceIds, roleId);
		}
		
		return ConstantUtil.Success;
	}
	
	@Override
	public List<Role> getRoleListByUserId(Integer userId)
	{
		return roleMapper.getRoleListByUserId(userId);
	}
	
	@Override
	public List<Role> getRoleList()
	{
		return roleMapper.getRoleList();
	}
	
	@Override
	public String getRoleDataTables(RoleSearchModel searchModel)
	{
		Integer total = getRoleTotalBySearch(searchModel);
		List<Role> roleList = getRoleListBySearch(searchModel);		
		if(roleList==null || roleList.size()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[",total,total));
		int i= 0;
		for(Role r:roleList)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,r);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	
	@Override
	public String getRoleDataRow(Integer id)
	{
		Role r = getRoleById(id);
		StringBuilder sb = new StringBuilder();
		addDataRow(sb,r);
		return sb.toString();
	}

	private void addDataRow(StringBuilder sb,Role r)
	{
		sb.append("[");
		sb.append("\"<input type=\\\"checkbox\\\" name=\\\"id[]\\\" value=\\\"").append(r.getId()).append("\\\">\"");
		sb.append(",").append(r.getId());
		sb.append(",\"").append(r.getName()).append("\"");
		sb.append(",\"").append(r.getRemark()).append("\"");
		sb.append(",\"").append(r.getUpdatePerson()).append("\"");
		sb.append(",\"").append(StringUtil.formatDate(r.getUpdateDate(),"yyyy-MM-dd HH:mm:ss")).append("\"");
		sb.append(",\"")
		.append("<a href=\\\"javascript:Role.update_click('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-edit\\\"></i> 修改</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:Role.remove('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-times\\\"></i> 删除</a>")
		.append("&nbsp;&nbsp;<a href=\\\"javascript:Role.assign_click('").append(r.getId()).append("');\\\" class=\\\"btn btn-xs default btn-editable\\\"><i class=\\\"fa fa-key\\\"></i> 分配权限</a>")
		.append("\"");
		sb.append("]");
	}
	
	@Override
	public String getRoleForOptions(Integer userId)
	{
		List<Role> assignRoles = getRoleListByUserId(userId);
		List<Role> allRoles = getRoleList();
		
		Map<Integer,Role> hmAssignRoles = new HashMap<Integer,Role>();
		for(Role r:assignRoles)
		{
			hmAssignRoles.put(r.getId(),r);
		}
		StringBuilder sb = new StringBuilder();
		for(Role r:allRoles)
		{
			sb.append("<option value=\"").append(r.getId()).append("\"");
			if(hmAssignRoles.containsKey(r.getId()))
			{
				sb.append(" selected");
			}
			sb.append(">").append(r.getName()).append("</option>");
		}
		return sb.toString();
	}
}
