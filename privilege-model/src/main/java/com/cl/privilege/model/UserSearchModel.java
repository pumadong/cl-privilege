package com.cl.privilege.model;

import java.io.Serializable;

/**
 * 用户查询SearchModel
 */
public class UserSearchModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer pageNo = 1;
	private Integer pageSize = PrivilegeModelConstant.PageSize;
	
	//登录用户名
	private String username;
	//真实姓名
	private String fullname;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
