package com.cl.privilege.api;

import com.cl.privilege.model.User;

public interface IPrivilegeBaseApiService {

	User getUserByUsername(String username);
}
