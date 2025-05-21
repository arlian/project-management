package com.example.project_management.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.project_management.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this(
          user.getId(),
          user.getUsername(),
          user.getPassword(),
          user.getName(),
          List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override public boolean isAccountNonExpired()   { return true; }
    @Override public boolean isAccountNonLocked()    { return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled()             { return true; }
}
