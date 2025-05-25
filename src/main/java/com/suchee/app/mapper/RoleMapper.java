package com.suchee.app.mapper;

import com.suchee.app.dto.RoleDTO;
import com.suchee.app.entity.Role;
import com.suchee.app.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "role", source = "role")
    RoleDTO toDto(Role rolep);

    Role toEntity(RoleDTO roleDTO);



}
