package com.suchee.app.service.impl;

import com.suchee.app.core.types.Email;
import com.suchee.app.dto.PasswordChangeDTO;
import com.suchee.app.dto.RoleDTO;
import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.entity.Role;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.enums.RoleType;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.RoleMapper;
import com.suchee.app.mapper.UserAccountMapper;
import com.suchee.app.messaging.async.AsyncEventPublishType;
import com.suchee.app.messaging.async.AsyncEventPublisher;
import com.suchee.app.messaging.async.impl.UserCreationEvent;
import com.suchee.app.repository.UserAccountRepository;
import com.suchee.app.security.SecurityContext;
import com.suchee.app.service.RoleService;
import com.suchee.app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // User Mapper
    private final UserAccountMapper userAccountMapper;

    // User Repository
    private final UserAccountRepository userAccountRepository;

    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final AsyncEventPublisher asyncEventPublisher;


    @Override
    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO) {

        if(Trace.userCreation){
            Trace.log("User creation start \n" , userCreateDTO.toString());
        }

        UserAccount userAccount = new UserAccount();

        this.userAccountMapper.mapUserCreateDTOtoUserAccount(userCreateDTO,userAccount);

        // check for role

        RoleDTO role = this.roleService.findByRoleType(RoleType.USER);

        if(role == null){
            throw new ResourceNotFoundException(Role.getEntityName(),"RoleType",RoleType.USER.getDisplayName());
        }

        if(Trace.userCreation){
            Trace.log("User appended with role : " + role.getRole().getDisplayName());
        }

        userAccount.setRoles(List.of(this.roleMapper.toEntity(role)));

        UserAccount savedAccount = this.userAccountRepository.save(userAccount);
        this.userAccountRepository.flush();
        if(Trace.userCreation){
            Trace.log("User created successfully with id " + savedAccount.getId());
        }

        UserDTO userDTO = this.userAccountMapper.toDto(savedAccount);

        UserCreationEvent userCreationEvent = new UserCreationEvent(userDTO, AsyncEventPublishType.QUEUE_EVENT);
        this.asyncEventPublisher.publish(userCreationEvent);

        // Map saved entity to output DTO
        return userDTO;
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
        Optional<UserAccount> optionalUserAccountWithThisEmail = this.userAccountRepository.findByEmail(new Email(email));

        return optionalUserAccountWithThisEmail.isEmpty();
    }

    @Override
    public UserDTO getUserByEmail(String email) {

        Optional<UserAccount> optionalUserAccountWithThisEmail = this.userAccountRepository.findByEmail(new Email(email));

        if(optionalUserAccountWithThisEmail.isEmpty()){
            throw new ResourceNotFoundException(UserAccount.getEntityName(),"email",email);
        }

        return this.userAccountMapper.toDto(optionalUserAccountWithThisEmail.get());
    }

    @Override
    public UserDTO getMe() {

        UserAccount userAccount = SecurityContext.getCurrentUserAccount();

        return this.userAccountMapper.toDto(userAccount);
    }
}
