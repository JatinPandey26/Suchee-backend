package com.suchee.app.entity;

import com.suchee.app.core.entities.VersionedEntity;
import com.suchee.app.core.types.Email;
import com.suchee.app.core.types.Password;
import com.suchee.app.core.types.converters.EmailConverter;
import com.suchee.app.core.types.converters.PasswordConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Setter
public class UserAccount extends VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true,nullable = false)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    private Password password;

    @Column(nullable = false)
    private LocalDate dob;

    @ManyToOne
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
