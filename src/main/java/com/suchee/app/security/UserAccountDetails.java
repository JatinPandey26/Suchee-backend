package com.suchee.app.security;

import com.suchee.app.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserAccountDetails implements UserDetails {

    private UserAccount userAccount;

    UserAccountDetails(UserAccount userAccount){
        this.userAccount=userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userAccount.getRole().getRole().getDisplayName()));
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail().getValue();
    }
}
