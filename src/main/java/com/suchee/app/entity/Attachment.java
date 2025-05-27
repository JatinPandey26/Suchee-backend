package com.suchee.app.entity;

import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.enums.FileType;
import com.suchee.app.enums.StorageProvider;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Attachment extends NonVersioned {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String previewUrl;

    @Column
    private UserAccount uploadedBy;

    @Column
    private Long fileSize;

    @Column
    @Enumerated(value = EnumType.STRING)
    private StorageProvider storageProvider;

    @Column
    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    @Column
    private String path;

    @Override
    public Long getId() {
        return 0L;
    }
}
