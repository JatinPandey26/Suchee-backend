package com.suchee.app.enums;

/**
 * Enum representing supported cloud storage providers.
 *
 * <p>Each enum constant holds the service name used for configuration or identification purposes.
 */
public enum StorageProvider {

    AWS("aws"),
    CLOUDINARY("cloudinary");

    private String serviceName;

    StorageProvider(String serviceName){
        this.serviceName = serviceName;
    }

    /**
     * Returns the service name associated with the storage provider.
     *
     * @return the storage provider's service name as a String
     */
    public String getServiceName() {
        return serviceName;
    }
}
