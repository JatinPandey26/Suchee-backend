package com.suchee.app.security;

import com.suchee.app.entity.Role;
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
        List<Role> roles = userAccount.getRoles();
        List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole().getDisplayName())).toList();
        return authorities;
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail().getValue();
    }

    public UserAccount getUserAccount(){
        return this.userAccount;
    }

}
