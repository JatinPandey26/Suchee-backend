package com.suchee.app.service;

import com.suchee.app.dto.AttachmentStorageInfoDto;
import com.suchee.app.dto.AttachmentUploadRequestDto;

/**
 * Service interface for file storage operations.
 *
 * <p>This interface defines the contract for uploading files and retrieving
 * storage-related information such as default folder paths or pre-upload URLs.
 * Implementations can integrate with different storage providers like
 * Cloudinary, AWS S3, or local filesystem.</p>
 */
public interface FileStorageService {

    /**
     * Uploads a file to the storage provider using information from the given upload request.
     *
     * @param attachmentUploadRequestDto the DTO containing file and metadata required for upload,
     *                                  such as file data, folder path, and public identifier
     * @return the publicly accessible URL or identifier of the uploaded file
     */
    String uploadFile(AttachmentUploadRequestDto attachmentUploadRequestDto);

    /**
     * Retrieves the default folder path configured for the storage provider.
     *
     * @return the default folder path as a string, typically used when no folder is specified explicitly
     */
    String getDefaultFolderPath();

    /**
     * Generates a pre-upload URL based on the provided storage information.
     * This URL can be used by clients to upload files directly or for preview purposes before actual upload.
     *
     * @param attachmentStorageInfo the DTO containing folder path and public ID info
     * @return a generated pre-upload URL string corresponding to the storage location
     */
    String generatePreUploadUrl(AttachmentStorageInfoDto attachmentStorageInfo);
}
