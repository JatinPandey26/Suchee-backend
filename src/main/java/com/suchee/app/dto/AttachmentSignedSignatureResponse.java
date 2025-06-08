package com.suchee.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentSignedSignatureResponse {
    String signature;
    String timeStamp;
    String folder;
}
