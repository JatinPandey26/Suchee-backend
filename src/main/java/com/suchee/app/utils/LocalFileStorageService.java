package com.suchee.app.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service component for managing temporary local file storage operations.
 *
 * <p>This utility class handles saving uploaded MultipartFile objects to a
 * temporary directory, retrieving files and their bytes by path, and deleting files.
 * It uses the system's default temporary directory for storing files.</p>
 *
 * <p>Typical use cases include saving files temporarily before uploading
 * them to a remote storage provider or for processing within the application.</p>
 */
@Component
public class LocalFileStorageService {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * Saves the given MultipartFile to the system's temporary directory with a unique filename.
     *
     * @param multipartFile the uploaded file to be saved temporarily
     * @return the File object representing the saved temporary file
     * @throws RuntimeException if any IO error occurs during file saving
     */
    public File saveTempFile(MultipartFile multipartFile)  {
        String uniqueName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        Path tempPath = Paths.get(TEMP_DIR, uniqueName);
        try {
            Files.copy(multipartFile.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save temp file", e);
        }
        return tempPath.toFile();
    }

    /**
     * Deletes the file at the specified absolute path.
     *
     * @param absolutePath the absolute file path to delete
     * @throws RuntimeException if the file does not exist or cannot be deleted
     */
    public void deleteFile(String absolutePath) {
        try {
            File toDelete = this.getFile(absolutePath);
            if (toDelete.exists() && !toDelete.delete()) {
                throw new RuntimeException("Failed to delete file at: " + absolutePath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found for deletion: " + absolutePath, e);
        }
    }

    /**
     * Retrieves a File object for the given path.
     *
     * @param path the absolute or relative path of the file
     * @return the File object if it exists and is a regular file
     * @throws FileNotFoundException if the file does not exist or is not a file
     */
    public File getFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file;
        }
        throw new FileNotFoundException("File not found at path: " + path);
    }

    /**
     * Reads and returns all bytes from the file at the specified path.
     *
     * @param path the absolute or relative path to the file
     * @return a byte array containing the contents of the file
     * @throws IOException if the file does not exist or cannot be read
     */
    public byte[] getFileBytes(String path) throws IOException {
        Path filePath = Paths.get(path).toAbsolutePath().normalize();
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found at: " + filePath);
        }
        return Files.readAllBytes(filePath);
    }

}
