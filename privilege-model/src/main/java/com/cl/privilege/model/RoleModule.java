package com.cl.privilege.model;

import java.io.Serializable;

public class RoleModule implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer roleId;

    private Integer moduleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}