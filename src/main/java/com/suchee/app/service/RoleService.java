package com.suchee.app.service;

import com.suchee.app.dto.RoleDTO;
import com.suchee.app.entity.Role;
import com.suchee.app.enums.RoleType;

import java.util.List;

public interface RoleService {

    Role findById(Long id);

    Role findByRoleType(RoleType roleType);

    List<Role> getAllRoles();

    boolean existsById(Long id);

    Role save(Role role);
}
