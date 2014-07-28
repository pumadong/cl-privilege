package com.cl.privilege.model;

import java.io.Serializable;

public class RoleResource implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer roleId;

    private Integer resourceId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}