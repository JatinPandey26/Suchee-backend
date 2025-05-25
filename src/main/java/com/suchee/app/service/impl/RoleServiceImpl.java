package com.suchee.app.service.impl;

import com.suchee.app.entity.Role;
import com.suchee.app.enums.RoleType;
import com.suchee.app.logging.LogLevel;
import com.suchee.app.logging.Trace;
import com.suchee.app.repository.RoleRepository;
import com.suchee.app.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public Role findByRoleType(RoleType roleType) {

        // check if role exists with this RoleType if Yes then return the role
        Optional<Role> roleInDB = this.roleRepository.findByRole(roleType);

        if(Trace.role){
            Trace.log("Searching for role with roleType : " + roleType.getDisplayName());
        }

        if(roleInDB.isPresent()){
            if(Trace.role){
                Trace.log("Role found with id " + roleInDB.get().getId());
            }
            return roleInDB.get();
        }

        Trace.log(LogLevel.ERROR,"Role with roleType : " , roleType.getDisplayName() , " not found in DB");

        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {

        // check if this role id exists in db

        return false;
    }

    @Override
    public Role save(Role role) {
        return null;
    }
}
