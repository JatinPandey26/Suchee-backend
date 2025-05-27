package com.suchee.app.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Cloudinary client.
 *
 * <p>This class reads Cloudinary credentials from the application properties
 * and exposes a {@link Cloudinary} bean to be used throughout the application
 * for uploading, deleting, and managing media files.</p>
 */
@Configuration
public class CloudinaryConfiguration {

    /**
     * Cloudinary cloud name, injected from application properties.
     */
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    /**
     * Cloudinary API key, injected from application properties.
     */
    @Value("${cloudinary.api_key}")
    private String apiKey;

    /**
     * Cloudinary API secret, injected from application properties.
     */
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    /**
     * Creates and configures a {@link Cloudinary} bean with the provided credentials.
     *
     * @return a configured {@link Cloudinary} instance
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
