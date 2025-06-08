package com.suchee.app.mapper;

import com.suchee.app.dto.TeamCreationDTO;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.entity.Team;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {AttachmentMapper.class})
public interface TeamMapper {


    Team toEntity(TeamCreationDTO teamCreationDTO);

    TeamDTO toDto(Team team);

    Team toEntity(TeamDTO teamDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeamFromDto(TeamDTO dto, @MappingTarget Team entity);


}
