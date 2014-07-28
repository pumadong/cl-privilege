package com.cl.privilege.biz;

import java.util.List;

import com.cl.privilege.model.Module;

/**
 * 模块表相关操作
 */
public interface IModuleService {
	
	/**
	 * 获取所有Module
	 * @return
	 */
	List<Module> getModuleList();
	
	/**
	 * 根据flag获取Module
	 * @param flag
	 * @return
	 */
	List<Module> getModuleListByFlag(String flag);
	
	/**
	 * 查询roleId具有权限的模块列表
	 * @param roleId
	 * @return
	 */
	List<Module> getModuleListByRoleId(Integer roleId);
	
	/**
	 * 查询userId具有权限的模块列表
	 * @param userId
	 * @return
	 */
	List<Module> getModuleListByUserId(Integer userId);

}
