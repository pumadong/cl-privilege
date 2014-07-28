package com.cl.privilege.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			if(s.startsWith("m-"))
			{
				moduleIds.add(Integer.parseInt(s.replace("m-", "")));
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
}
