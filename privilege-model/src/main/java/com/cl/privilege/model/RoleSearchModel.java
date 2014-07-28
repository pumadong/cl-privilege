package com.cl.privilege.model;

import java.io.Serializable;

/**
 * 角色查询SearchModel
 */
public class RoleSearchModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer pageNo = 1;
	private Integer pageSize = PrivilegeModelConstant.PageSize;	
	//角色名称
	private String name;
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
