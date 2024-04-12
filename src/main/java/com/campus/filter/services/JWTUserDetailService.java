package com.campus.filter.services;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.campus.filter.repositories.RepositoryUser;
import com.campus.filter.repositories.entities.RolType;
import com.campus.filter.repositories.entities.UserE;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserDetailService implements UserDetailsService {

    private final RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username){
        UserE userOptional = repositoryUser.findByEmail(username);

        return new User(userOptional.getEmail(), userOptional.getPassword(), getAuthorities(userOptional.getRole()));
    }

    private List<GrantedAuthority> getAuthorities(RolType role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }



}