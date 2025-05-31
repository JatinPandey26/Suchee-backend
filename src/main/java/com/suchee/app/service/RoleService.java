package com.suchee.app.service;

import com.suchee.app.dto.RoleDTO;
import com.suchee.app.entity.Role;
import com.suchee.app.enums.RoleType;

import java.util.List;

public interface RoleService {

    RoleDTO findById(Long id);

    RoleDTO findByRoleType(RoleType roleType);

    List<RoleDTO> getAllRoles();

    boolean existsById(Long id);

}
