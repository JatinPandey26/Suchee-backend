package com.suchee.app.mapper;

import com.suchee.app.dto.MemberCreateDTO;
import com.suchee.app.dto.MemberDTO;
import com.suchee.app.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {UserAccountMapper.class,TeamMapper.class})
public interface MemberMapper {

    MemberDTO toDto(Member member);
    Member toEntity(MemberDTO memberDTO);

    Member toEntityFromCreateDto(MemberCreateDTO memberCreateDTO);
}
