package com.cl.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.cl.privilege.model.User;
import com.cl.privilege.model.UserSearchModel;


public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    // 以上是为了节约开发时间，使用MyBatisGenerator生成的代码
    // 以下是针对不足功能，添加的代码
    
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User getUserByUsername(@Param("username")String username);
    
	/**
	 * 根据条件查询用户总数
	 * @param searchModel
	 * @return
	 */
	Integer getUserTotalBySearch(UserSearchModel searchModel);
	
	/**
	 * 根据条件查询用户List
	 * @param searchModel
	 * @return
	 */
	List<User> getUserListBySearch(UserSearchModel searchModel,RowBounds rowBounds);
	
	/**
	 * 根据id删除用户关联的角色
	 * @param id
	 * @return
	 */
	Integer deleteUserRoleById(Integer id);	
	
	/**
	 * 对用户赋予角色
	 * @param roleIds
	 * @param userId
	 */
	void assignRoles(@Param("roleIds")List<Integer> roleIds,@Param("userId")Integer userId);
}