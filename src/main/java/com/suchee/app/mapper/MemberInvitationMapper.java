package com.suchee.app.mapper;

import com.suchee.app.dto.MemberInvitationDto;
import com.suchee.app.entity.MemberInvitation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = UserAccountMapper.class)
public interface MemberInvitationMapper {

    MemberInvitationDto toDto(MemberInvitation memberInvitation);

}
