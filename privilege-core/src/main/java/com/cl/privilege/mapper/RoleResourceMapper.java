package com.cl.privilege.mapper;

import com.cl.privilege.model.RoleResource;

public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);
}