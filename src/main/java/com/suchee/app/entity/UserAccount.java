package com.suchee.app.entity;

import com.suchee.app.core.entities.VersionedEntity;
import com.suchee.app.core.types.Email;
import com.suchee.app.core.types.Password;
import com.suchee.app.core.types.converters.EmailConverter;
import com.suchee.app.core.types.converters.PasswordConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class UserAccount extends VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column
    @Convert(converter = PasswordConverter.class)
    private Password password;

    @Column
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @Override
    public Long getId() {
        return this.id;
    }

    public static String getEntityName() {
        return "UserAccount";
    }
}
