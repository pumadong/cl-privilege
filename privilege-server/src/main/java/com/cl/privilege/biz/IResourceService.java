package com.cl.privilege.biz;

import java.util.List;

import com.cl.privilege.model.Resource;
import com.cl.privilege.model.User;

public interface IResourceService {

	/**
	 * 获取所有Resource
	 * @return
	 */
	List<Resource> getResourceList();
	
	/**
	 * 根据所属模块获取资源
	 * @return
	 */
	List<Resource> getResourceListByModuleFlag(List<String> moduleFlags,Integer userId);
	
	/**
	 * 根据id获取Resource对象
	 * @param id
	 * @return
	 */
	Resource getResourceById(Integer id);
	
	/**
	 * 新增记录
	 * @param resource
	 * @param user
	 * @return
	 */
	Integer createResource(Resource resource,User user);
	
	/**
	 * 根据id修改一条记录
	 * @param resource
	 * @param user
	 * @return
	 */
	Integer updateResourceById(Resource resource,User user);
	
	/**
	 * 根据id删除一条resource
	 * @param id
	 * @return
	 */
	Integer deleteResourceById(Integer id);
	
	
	/**
	 * 根据资源id判断是否被角色使用
	 * @param resourceId
	 * @return
	 */
	Boolean isUsedByRole(Integer resourceId);
	
	/**
	 * 给定角色具有权限的资源列表
	 * @param roleId
	 * @return
	 */
	List<Resource> getResourceListByRoleId(Integer roleId);
}
