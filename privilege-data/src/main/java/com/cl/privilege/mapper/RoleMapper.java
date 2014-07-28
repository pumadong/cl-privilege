package com.cl.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.cl.privilege.model.Role;
import com.cl.privilege.model.RoleSearchModel;


public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    // 以上是为了节约开发时间，使用MyBatisGenerator生成的代码
    // 以下是针对不足功能，添加的代码
    
	/**
	 * 根据id删除角色
	 * @param id
	 * @return
	 */
	Integer deleteRoleById(Integer id);
	
	/**
	 * 根据id删除角色模块关联关系
	 * @param roleId
	 * @return
	 */
	Integer deleteRoleModuleById(Integer roleId);
	
	/**
	 * 根据id删除角色资源关联关系
	 * @param roleId
	 * @return
	 */
	Integer deleteRoleResourceById(Integer roleId);
    
	/**
	 * 根据条件查询角色列表总数
	 * @param searchModel
	 * @return
	 */
	Integer getRoleTotalBySearch(RoleSearchModel searchModel);
	
	/**
	 * 根据条件查询用户List
	 * @param searchModel
	 * @return
	 */
	List<Role> getRoleListBySearch(RoleSearchModel searchModel,RowBounds rowBounds);
	
	/**
	 * 角色是否被用户使用
	 * @param roleId
	 * @return
	 */
	Boolean isUsedByUser(Integer roleId);
	
	/**
	 * 分配角色关联模块
	 * @param moduleIds
	 * @param roleId
	 */
	void assignModules(@Param("moduleIds")List<Integer> moduleIds,@Param("roleId")Integer roleId);	

	/**
	 * 分配角色关联资源
	 * @param resourceIds
	 * @param roleId
	 */
	void assignResources(@Param("resourceIds")List<Integer> resourceIds,@Param("roleId")Integer roleId);
	
	/**
	 * 根据userId获取关联的权限列表
	 * @param userId
	 * @return
	 */
	List<Role> getRoleListByUserId(@Param("userId")Integer userId);
	
	/**
	 * 所有角色列表
	 * @return
	 */
	List<Role> getRoleList();
}