package com.cl.privilege.mapper;

import com.cl.privilege.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}