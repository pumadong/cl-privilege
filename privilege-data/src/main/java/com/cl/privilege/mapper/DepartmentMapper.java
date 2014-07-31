package com.cl.privilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.privilege.model.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    // 以上是为了节约开发时间，使用MyBatisGenerator生成的代码
    // 以下是针对不足功能，添加的代码
    
	/**
	 * 查询所有Department对象
	 * @return
	 */
	List<Department> getDepartmentList();
	/**
	 * 查询所有Department对象
	 * @return
	 */
	List<Department> getDepartmentListByParentId(@Param("parentId")Integer parentId);
	/**
	 * 部门是否被用户使用
	 * @param departmentId
	 */
	Boolean isUsedByUser(Integer departmentId);
}