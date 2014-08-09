package com.cl.privilege.biz;

import java.util.List;

import com.cl.privilege.model.User;
import com.cl.privilege.model.UserSearchModel;



/**
 * 用户表相关操作
 */
public interface IUserService {
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User getUserByUsername(String username);
	
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User getUserById(Integer id);
	
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
	List<User> getUserListBySearch(UserSearchModel searchModel);
	
	/**
	 * 新增用户
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer createUser(User user,User operator);
	
	/**
	 * 根据id更新用户
	 * @param user
	 * @param operator
	 * @return
	 */
	Integer updateUserById(User user,User operator);
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	Integer deleteUserById(Integer id,User operator);
	
	/**
	 * 用户赋予角色
	 * @param id
	 * @param selectedStr
	 * @return
	 */
	String assignRole(Integer id,String selectedStr);
	
	/**
	 * 根据查询条件，返回DataTables控件需要的Json数据格式
	 * @param searchModel
	 * @return
	 */
	String getUserDataTables(UserSearchModel searchModel);
	
	/**
	 * 返回DataTables控件需要的一行Json数据格式
	 * @param id
	 * @return
	 */
	String getUserDataRow(Integer id);
}
