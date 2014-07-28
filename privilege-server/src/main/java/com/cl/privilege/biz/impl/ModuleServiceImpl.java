package com.cl.privilege.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.privilege.biz.IModuleService;
import com.cl.privilege.mapper.ModuleMapper;
import com.cl.privilege.model.Module;

@Service
public class ModuleServiceImpl implements IModuleService {
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Override
	public List<Module> getModuleList()
	{
		return moduleMapper.getModuleList();
	}
	
	@Override
	public List<Module> getModuleListByFlag(String flag)
	{
		return moduleMapper.getModuleListByFlag(flag);
	}
	
	@Override
	public List<Module> getModuleListByRoleId(Integer roleId)
	{
		return moduleMapper.getModuleListByRoleId(roleId);
	}
	
	@Override
	public List<Module> getModuleListByUserId(Integer userId)
	{
		return moduleMapper.getModuleListByUserId(userId);
	}
}
