package com.suchee.app.service.impl;

import com.cloudinary.Cloudinary;
import com.suchee.app.service.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link FileStorageService} that integrates with Cloudinary
 * to handle file uploads.
 *
 * <p>This service handles the upload of {@link MultipartFile} objects to
 * Cloudinary's cloud storage. It supports storing files in custom folders
 * and retrieving the default folder path from configuration.</p>
 *
 * <p>Configuration properties used:
 * <ul>
 *   <li><b>cloudinary.defaults.folder</b> - default folder in Cloudinary (default is "public")</li>
 * </ul>
 *
 * <p>This service can be extended to include delete, rename, or transformation operations if needed.</p>
 *
 * @author
 */

@Service
public class CloudinaryServiceImpl implements FileStorageService {

    @Resource
    private Cloudinary cloudinary;

    @Value("${cloudinary.defaults.folder:public}")
    String defaultFolder;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDefaultFolderPath() {
        return defaultFolder;
    }
}
