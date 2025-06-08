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
import java.util.List;

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

    @Column(length = 600)
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_account_x_role",
                joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}
                )
    private List<Role> roles;

    @Override
    public Long getId() {
        return this.id;
    }

    public static String getEntityName() {
        return "UserAccount";
    }
}
