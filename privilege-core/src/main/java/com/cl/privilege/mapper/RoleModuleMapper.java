package com.cl.privilege.mapper;

import com.cl.privilege.model.RoleModule;

public interface RoleModuleMapper {
    int insert(RoleModule record);

    int insertSelective(RoleModule record);
}