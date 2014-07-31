package com.cl.privilege.biz;

import java.util.List;

import com.cl.privilege.model.Department;
import com.cl.privilege.model.User;


/**
 * 部门表相关操作
 */
public interface IDepartmentService {

	/**
	 * 查询所有Department对象
	 * @return
	 */
	List<Department> getDepartmentList();
	
	/**
	 * 根据id获取Department对象
	 * @param id
	 * @return
	 */
	Department getDepartmentById(Integer id);
	
	/**
	 * 新增记录
	 * @param department
	 * @param user
	 * @return
	 */
	Integer createDepartment(Department department,User user);
	
	/**
	 * 根据id修改一条记录
	 * @param department
	 * @param user
	 * @return
	 */
	Integer updateDepartmentById(Department department,User user);
	
	/**
	 * 根据id删除一条记录
	 * @param id
	 * @return
	 */
	Integer deleteDepartmentById(Integer id);
	
	/**
	 * 部门是否被用户使用
	 * @param departmentId
	 */
	Boolean isUsedByUser(Integer departmentId);
	
	String getDepartmentTree();
	
}
