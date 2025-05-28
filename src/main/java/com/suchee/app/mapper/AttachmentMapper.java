package com.suchee.app.mapper;

import com.suchee.app.dto.AttachmentDTO;
import com.suchee.app.entity.Attachment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",uses = {UserAccountMapper.class})
public interface AttachmentMapper {

    AttachmentDTO toDto(Attachment attachment);
    Attachment toEntity(AttachmentDTO attachmentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapAttachmentFromAttachmentDTO(AttachmentDTO attachmentDTO , @MappingTarget Attachment attachment);

}
