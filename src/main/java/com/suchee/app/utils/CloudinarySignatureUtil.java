package com.suchee.app.utils;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CloudinarySignatureUtil {

    private final Cloudinary cloudinary;

    /**
     * Generates a Cloudinary upload signature.
     * @param params Map of upload params (e.g. folder, timestamp, etc.)
     * @param apiSecret Your Cloudinary API secret
     * @return signature string
     */
    public String generateSignature(Map<String, Object> params, String apiSecret) {
        return cloudinary.apiSignRequest(params,apiSecret);
    }
}
