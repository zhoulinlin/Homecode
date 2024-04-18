package com.homecode.service;

import com.homecode.model.AccessInfo;
import com.homecode.model.RoleInfo;

public interface AccessService {
	AccessInfo getAccessInfo(long userId);

	void addAccess(AccessInfo access);

	RoleInfo validateRoleFromAuthHeader(String authHeader);
}
