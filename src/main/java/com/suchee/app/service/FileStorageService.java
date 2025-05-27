package com.suchee.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * A service interface for file storage operations.
 *
 * <p>This interface defines methods to upload files and retrieve default
 * storage paths. It can be implemented for various storage providers like
 * Cloudinary, AWS S3, local filesystem, etc.</p>
 */
public interface FileStorageService {

    /**
     * Uploads a file to a specified folder in the storage provider.
     *
     * @param file       the file to upload
     * @param folderName the name of the folder where the file will be stored
     * @return the accessible URL or identifier of the uploaded file
     */
    String uploadFile(MultipartFile file, String folderName);

    /**
     * Returns the default folder path configured for the storage provider.
     *
     * @return the default folder path as a string
     */
    String getDefaultFolderPath();
}
