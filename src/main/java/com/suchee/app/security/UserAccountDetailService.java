package com.suchee.app.security;

import com.suchee.app.core.types.Email;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.logging.LogLevel;
import com.suchee.app.logging.Trace;
import com.suchee.app.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAccountDetailService implements UserDetailsService {

    UserAccountRepository userAccountRepository;

    UserAccountDetailService(UserAccountRepository userAccountRepository){
        this.userAccountRepository=userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // TODO: load user from cache

        if(Trace.security){
            Trace.log(LogLevel.DEBUG,"Loading user from db : email - " + email);
        }
        // load user from db

        Optional<UserAccount> userAccount = this.userAccountRepository.findByEmail(new Email(email));

        if(userAccount.isEmpty()){
            Trace.log(LogLevel.ERROR,"User with email : " + email + " not  found ");
            throw new UsernameNotFoundException("User with email : " + email + " not  found ");
        }

        return new UserAccountDetails(userAccount.get());
    }
}
