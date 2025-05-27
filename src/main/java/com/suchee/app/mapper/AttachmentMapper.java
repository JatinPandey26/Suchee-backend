package com.suchee.app.mapper;

import com.suchee.app.dto.AttachmentDTO;
import com.suchee.app.entity.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {UserAccountMapper.class})
public interface AttachmentMapper {

    AttachmentDTO toDto(Attachment attachment);
    Attachment toEntity(AttachmentDTO attachmentDTO);

}
