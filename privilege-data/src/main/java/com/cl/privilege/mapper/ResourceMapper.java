package com.cl.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.privilege.model.Resource;

public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    // 以上是为了节约开发时间，使用MyBatisGenerator生成的代码
    // 以下是针对不足功能，添加的代码
    
	/**
	 * 获取所有resource
	 * @return
	 */
	List<Resource> getResourceList();
	
	/**
	 * 根据所属模块获取资源
	 * @return
	 */
	List<Resource> getResourceListByModuleFlag(@Param("moduleFlags")List<String> moduleFlags,@Param("userId")Integer userId);
	
	/**
	 * 根据资源id判断是否被角色使用
	 * @param resourceId
	 * @return
	 */
	Boolean isUsedByRole(@Param("resourceId") Integer resourceId);
	
	/**
	 * 给定角色具有权限的资源列表
	 * @param roleId
	 * @return
	 */
	List<Resource> getResourceListByRoleId(Integer roleId);
	
	/**
	 * 根据parentId获取资源
	 * @param parentId
	 * @return
	 */
	List<Resource> getResourceListByParentId(@Param("parentId")Integer parentId);	
}