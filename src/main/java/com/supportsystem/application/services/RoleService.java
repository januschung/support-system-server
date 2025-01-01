package com.supportsystem.application.services;

import com.supportsystem.application.domains.Role;

public interface RoleService {

    Role createRole(String roleName);

    Role getRoleByName(String roleName);
}
