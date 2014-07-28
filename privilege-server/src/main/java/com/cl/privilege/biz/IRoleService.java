package com.cl.privilege.biz;

import java.util.List;

import com.cl.privilege.model.Role;
import com.cl.privilege.model.RoleSearchModel;
import com.cl.privilege.model.User;

/**
 * 角色表相关操作
 */
public interface IRoleService {
	/**
	 * 根据id获取Role对象
	 * @param id
	 * @return
	 */
	Role getRoleById(Integer id);
	
	/**
	 * 新增记录
	 * @param role
	 * @param user
	 * @return
	 */
	Integer createRole(Role role,User user);

	/**
	 * 根据id修改一条记录
	 * @param role
	 * @return
	 */
	Integer updateRoleById(Role role,User user);
	
	/**
	 * 根据id删除角色
	 * @param id
	 * @return
	 */
	Integer deleteRoleById(Integer id);
	
	/**
	 * 根据条件查询角色列表总数
	 * @return
	 */
	Integer getRoleTotalBySearch(RoleSearchModel searchModel);
	
	/**
	 * 根据条件查询角色列表
	 * @return
	 */
	List<Role> getRoleListBySearch(RoleSearchModel searchModel);
	
	/**
	 * 角色是否被用户使用
	 * @param roleId
	 * @return
	 */
	Boolean isUsedByUser(Integer roleId);
	
	/**
	 * 根据选择的字符串(,分割)，对角色赋予模块和资源
	 * @param roleId
	 * @param checkedStr
	 */
	String assignModuleAndResource(Integer roleId,String checkedStr);
	
	/**
	 * 根据userId获取关联的权限列表
	 * @param userId
	 * @return
	 */
	List<Role> getRoleListByUserId(Integer userId);
	
	/**
	 * 所有角色列表
	 * @return
	 */
	List<Role> getRoleList();
}
