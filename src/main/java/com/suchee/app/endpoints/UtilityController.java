package com.suchee.app.endpoints;

import com.suchee.app.dto.AttachmentSignedSignatureResponse;
import com.suchee.app.service.FileStorageService;
import com.suchee.app.service.impl.CloudinaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/utility")
public class UtilityController {

    private final FileStorageService fileStorageService;

    @GetMapping("/public/attachmentUploadSignature")
    public ResponseEntity<AttachmentSignedSignatureResponse> getSignedUrlForUpload(@RequestParam String path){

        AttachmentSignedSignatureResponse signedKey = null;
        if(fileStorageService instanceof CloudinaryServiceImpl){
          signedKey = ((CloudinaryServiceImpl) fileStorageService).generateSignedSignature(path);
        }

        if(signedKey == null){
            throw new RuntimeException("Signed key not generated");
        }

        return ResponseEntity.ok(signedKey);
    }

}
