package com.suchee.app.mapper;

import com.suchee.app.core.types.Email;
import com.suchee.app.core.types.Password;
import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "snapshot", ignore = true)
    @Mapping(target = "new", ignore = true)
    @Mapping(target = "lastUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserAccount toEntity(UserDTO userDTO);

    UserDTO toDto(UserAccount userAccount);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "snapshot", ignore = true)
    @Mapping(target = "new", ignore = true)
    @Mapping(target = "lastUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void mapUserCreateDTOtoUserAccount(UserCreateDTO userCreateDTO , @MappingTarget UserAccount userAccount);

    List<UserDTO> toDtos(List<UserAccount> userAccounts);

    default Email map(String value) {
        return value == null ? null : new Email(value);
    }

    default String map(Email email) {
        return email == null ? null : email.getValue(); // or email.toString()
    }

    default Password mapPassword(String value) {
        return value == null ? null : new Password(value);
    }

    default String mapPassword(Password password) {
        return password == null ? null : password.getValue();
    }

}
