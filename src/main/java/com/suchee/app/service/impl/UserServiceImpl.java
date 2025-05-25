package com.suchee.app.service.impl;

import com.suchee.app.core.types.Password;
import com.suchee.app.dto.PasswordChangeDTO;
import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.entity.Role;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.UserAccountMapper;
import com.suchee.app.repository.UserAccountRepository;
import com.suchee.app.service.RoleService;
import com.suchee.app.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    // User Mapper
    private UserAccountMapper userAccountMapper;

    // User Repository
    private UserAccountRepository userAccountRepository;

    private RoleService roleService;

    UserServiceImpl(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper , RoleService roleService){
        this.userAccountRepository=userAccountRepository;
        this.userAccountMapper=userAccountMapper;
        this.roleService=roleService;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO) {

        if(Trace.userCreation){
            Trace.log("User creation start \n" , userCreateDTO.toString());
        }

        UserAccount userAccount = new UserAccount();

        this.userAccountMapper.mapUserCreateDTOtoUserAccount(userCreateDTO,userAccount);

        // check for role

        Role role = this.roleService.findByRoleType(userCreateDTO.getRoleDto().getRole());

        if(role == null){
            throw new ResourceNotFoundException(Role.getEntityName(),"RoleType",userCreateDTO.getRoleDto().getRole());
        }

        if(Trace.userCreation){
            Trace.log("User appended with role : " + role.getRole().getDisplayName());
        }
        userAccount.setRole(role);

        UserAccount savedAccount = this.userAccountRepository.save(userAccount);

        if(Trace.userCreation){
            Trace.log("User created successfully with id " + savedAccount.getId());
        }

        // Map saved entity to output DTO
        return this.userAccountMapper.toDto(savedAccount);
    }

    @Override
    public UserDTO getUserById(Long id) {

        Optional<UserAccount> optionalUserAccount = this.userAccountRepository.findById(id);

        if(optionalUserAccount.isPresent()){
            return this.userAccountMapper.toDto(optionalUserAccount.get());
        }

        throw new ResourceNotFoundException(UserAccount.getEntityName(),id);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<UserAccount> userAccounts = this.userAccountRepository.findAll();

        List<UserDTO> userDTOS = this.userAccountMapper.toDtos(userAccounts);

        return userDTOS;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userUpdateDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public void changeUserPassword(Long userId, PasswordChangeDTO passwordChangeDTO) {

    }

    @Override
    public void assignRoleToUser(Long userId, String roleName) {

    }

    @Override
    public boolean isEmailAvailable(String email) {
        return false;
    }
}
