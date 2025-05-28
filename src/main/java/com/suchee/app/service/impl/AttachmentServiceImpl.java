package com.suchee.app.service.impl;

import com.suchee.app.dto.AttachmentDTO;
import com.suchee.app.dto.AttachmentStorageInfoDto;
import com.suchee.app.dto.AttachmentUploadRequestDto;
import com.suchee.app.entity.Attachment;
import com.suchee.app.enums.AttachmentUploadStatus;
import com.suchee.app.enums.FileType;
import com.suchee.app.enums.StorageProvider;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.AttachmentMapper;
import com.suchee.app.messaging.async.impl.AttachmentCreatedEventMessage;
import com.suchee.app.repository.AttachmentRepository;
import com.suchee.app.service.AttachmentService;
import com.suchee.app.service.FileStorageService;
import com.suchee.app.utils.EventIdGenerator;
import com.suchee.app.utils.LocalFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for processing, storing, and managing attachments.
 * <p>
 * This service handles file uploads, persists metadata, publishes async events for
 * further processing, and manages local temporary storage of files.
 * </p>
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final FileStorageService fileStorageService;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private LocalFileStorageService localFileStorageService;

    public AttachmentServiceImpl(FileStorageService fileStorageService,
                                 AttachmentRepository attachmentRepository,
                                 AttachmentMapper attachmentMapper) {
        this.fileStorageService = fileStorageService;
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    /**
     * Processes and saves a single file attachment.
     * <p>
     * Generates a pre-upload URL, persists the attachment metadata,
     * saves the file temporarily locally, and publishes an async event
     * to handle the actual upload.
     * </p>
     *
     * @param file                  the multipart file to process
     * @param attachmentStorageInfo the storage info containing folder path and public id
     * @return the saved Attachment entity
     */
    public Attachment process(MultipartFile file, AttachmentStorageInfoDto attachmentStorageInfo) {

        if (Trace.attachment) {
            Trace.log("Starting to process attachment in folder: " + attachmentStorageInfo.getFolderPath()
                    + " publicId : " + attachmentStorageInfo.getPublicId());
            Trace.debug("File name: ", file.getOriginalFilename(), "Size: ", file.getSize());
        }

        String preUploadUrl = this.fileStorageService.generatePreUploadUrl(attachmentStorageInfo);

        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setFileSize(file.getSize());
        attachment.setFileType(FileType.fromExtension(file.getOriginalFilename()));
        attachment.setStorageProvider(StorageProvider.CLOUDINARY);
        attachment.setPath(attachmentStorageInfo.getFolderPath());
        attachment.setPreUploadUrl(preUploadUrl);
        // For now, set url and previewUrl as preUploadUrl until actual upload completes
        attachment.setUrl(preUploadUrl);
        attachment.setPreviewUrl(preUploadUrl);

        Attachment savedAttachment = this.attachmentRepository.save(attachment);

        if (Trace.attachment) {
            Trace.log("Attachment metadata saved with ID: " + savedAttachment.getId());
        }

        // Prepare DTO for async upload event
        AttachmentUploadRequestDto attachmentUploadRequestDto = new AttachmentUploadRequestDto();
        attachmentUploadRequestDto.setAttachmentId(savedAttachment.getId());

        // Save the file temporarily on local disk
        File localFile = this.localFileStorageService.saveTempFile(file);

        attachmentUploadRequestDto.setFilePath(localFile.getAbsolutePath());
        attachmentUploadRequestDto.setFolderPath(attachmentStorageInfo.getFolderPath());
        attachmentUploadRequestDto.setPreUploadUrl(preUploadUrl);
        attachmentUploadRequestDto.setPublicId(attachmentStorageInfo.getPublicId());

        // Publish async event for uploading the file and cleaning local file
        AttachmentCreatedEventMessage attachmentCreatedEventMessage = new AttachmentCreatedEventMessage(
                EventIdGenerator.generateId(),
                this.getClass().getName(),
                attachmentUploadRequestDto
        );

        this.publisher.publishEvent(attachmentCreatedEventMessage);

        if (Trace.attachment) {
            Trace.log("Attachment created event published for attachment ID: " + savedAttachment.getId());
            Trace.log("Attachment saved with URL: " + savedAttachment.getUrl());
        }

        return savedAttachment;
    }

    /**
     * Processes and saves multiple file attachments.
     * <p>
     * Iterates over the list of files, processing each individually.
     * </p>
     *
     * @param files                 the list of files to process
     * @param attachmentStorageInfo the storage info containing folder path and public id
     * @return the list of saved Attachment entities
     */
    @Override
    public List<Attachment> process(List<MultipartFile> files, AttachmentStorageInfoDto attachmentStorageInfo) {
        if (Trace.attachment) {
            Trace.log("Processing " + files.size() + " attachments in folder: "
                    + attachmentStorageInfo.getFolderPath() + " , publicId : " + attachmentStorageInfo.getPublicId());
        }

        return files.stream()
                .map(file -> process(file, attachmentStorageInfo))
                .toList();
    }

    /**
     * Handles asynchronous update of attachment after file upload.
     * <p>
     * Updates attachment status and URLs upon successful upload,
     * and cleans up local temporary files.
     * </p>
     *
     * @param attachmentUploadRequestDto the upload request DTO containing file and attachment info
     * @return the updated AttachmentDTO
     * @throws ResourceNotFoundException if attachment with given ID is not found
     * @throws RuntimeException          if the uploaded URL does not match the pre-upload URL
     */
    @Override
    public AttachmentDTO updateAttachmentAfterUploadAsync(AttachmentUploadRequestDto attachmentUploadRequestDto) {

        Optional<Attachment> attachmentOptional = this.attachmentRepository.findById(attachmentUploadRequestDto.getAttachmentId());

        if (attachmentOptional.isEmpty()) {
            throw new ResourceNotFoundException(Attachment.getEntityName(), attachmentUploadRequestDto.getAttachmentId());
        }

        Attachment attachment = attachmentOptional.get();

        String url = "";
        try {
            url = this.fileStorageService.uploadFile(attachmentUploadRequestDto);
            attachment.setAttachmentUploadStatus(AttachmentUploadStatus.COMPLETED);
            if (Trace.attachment) {
                Trace.log("File uploaded successfully for attachment ID: " + attachment.getId());
            }
        } catch (Exception exception) {
            attachment.setAttachmentUploadStatus(AttachmentUploadStatus.FAILED);
            if (Trace.attachment) {
                Trace.log("File upload failed for attachment ID: " + attachment.getId() + " Error: " + exception.getMessage());
            }
        }

        // Verify uploaded URL matches pre-upload URL
        if (!attachment.getPreUploadUrl().trim().equals(url.trim())) {
            if (Trace.attachment) {
                Trace.log("Uploaded URL does not match pre-upload URL for attachment ID: " + attachment.getId());
            }
            throw new RuntimeException("Uploaded URL mismatch");
        }

        attachment.setUrl(url);
        attachment.setPreviewUrl(url);

        Attachment updatedAttachment = this.attachmentRepository.save(attachment);

        // Clean up local temporary file
        this.localFileStorageService.deleteFile(attachmentUploadRequestDto.getFilePath());

        if (Trace.attachment) {
            Trace.log("Local temporary file deleted for attachment ID: " + attachment.getId());
        }

        return this.attachmentMapper.toDto(updatedAttachment);
    }

}
