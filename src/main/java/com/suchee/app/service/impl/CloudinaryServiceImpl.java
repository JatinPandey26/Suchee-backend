package com.suchee.app.service.impl;

import com.cloudinary.Cloudinary;
import com.suchee.app.dto.AttachmentStorageInfoDto;
import com.suchee.app.dto.AttachmentUploadRequestDto;
import com.suchee.app.service.FileStorageService;
import com.suchee.app.utils.LocalFileStorageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link FileStorageService} that integrates with Cloudinary
 * cloud storage service to handle file upload operations.
 *
 * <p>This service supports uploading files provided as {@link MultipartFile} or
 * files stored temporarily on local disk, and it uploads them to Cloudinary.
 * Files can be stored under a specified folder path with a designated public ID.</p>
 *
 * <p>The service also supports generating URLs for uploaded files and
 * retrieving the default folder path configured in application properties.</p>
 *
 * <p>Configuration properties used:
 * <ul>
 *   <li><b>cloudinary.defaults.folder</b> - the default folder name to store files in Cloudinary (default is "public")</li>
 * </ul>
 * </p>
 *
 * <p>This class depends on the {@link Cloudinary} client bean and {@link LocalFileStorageService}
 * for temporary local file operations.</p>
 *
 * <p>Extensible to support additional file operations such as delete or rename if needed.</p>
 *
 * @author
 */
@Service
public class CloudinaryServiceImpl implements FileStorageService {

    @Resource
    private Cloudinary cloudinary;

    @Value("${cloudinary.defaults.folder:public}")
    String defaultFolder;

    @Autowired
    private LocalFileStorageService localFileStorageService;

    /**
     * Uploads a file to Cloudinary based on the provided {@link AttachmentUploadRequestDto}.
     * <p>
     * If the {@link MultipartFile} is present in the request DTO, the file bytes
     * are read directly from it. Otherwise, the file bytes are read from a local
     * temporary file path specified in the DTO.
     * </p>
     * <p>
     * The file is uploaded to the Cloudinary folder and public ID specified in the request.
     * Returns the secure URL of the uploaded file.
     * </p>
     *
     * @param attachmentUploadRequest the DTO containing file, folder path, and public ID information for upload
     * @return the secure URL of the uploaded file in Cloudinary
     * @throws RuntimeException if an {@link IOException} occurs during file reading or upload
     */
    @Override
    public String uploadFile(AttachmentUploadRequestDto attachmentUploadRequest) {
        try {
            MultipartFile file = attachmentUploadRequest.getFile();
            byte[] fileBytes;

            if (file != null) {
                fileBytes = file.getBytes();
            } else {
                // If file is null, it can be an async flow using a local temp file
                String filePath = attachmentUploadRequest.getFilePath();
                fileBytes = this.localFileStorageService.getFileBytes(filePath);
            }

            HashMap<Object, Object> options = new HashMap<>();
            options.put("public_id", attachmentUploadRequest.getPublicId());
            options.put("folder", attachmentUploadRequest.getFolderPath());

            Map uploadedFile = cloudinary.uploader().upload(fileBytes, options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the default folder path configured for storing files in Cloudinary.
     *
     * @return the default folder path as a string
     */
    @Override
    public String getDefaultFolderPath() {
        return defaultFolder;
    }

    /**
     * Generates a secure pre-upload URL for a file based on the folder path and public ID.
     * <p>
     * This URL is generated from the Cloudinary service using the concatenation of
     * the folder path and public ID provided in the {@link AttachmentStorageInfoDto}.
     * </p>
     *
     * @param attachmentStorageInfo DTO containing folder path and public ID for the attachment
     * @return a secure URL string for pre-upload reference to the file
     */
    @Override
    public String generatePreUploadUrl(AttachmentStorageInfoDto attachmentStorageInfo) {
        return cloudinary.url()
                .secure(true)
                .generate(attachmentStorageInfo.getFolderPath() + "/" + attachmentStorageInfo.getPublicId());
    }
}
