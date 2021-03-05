package com.embl.assessment.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "21232f297a57a5a743894a0e4a801fc3";
    private static final String ROLE = "admin";

    private static final UserDetailImpl ONLY_USER = new UserDetailImpl(USER_NAME, PASSWORD, Arrays.asList(new SimpleGrantedAuthority(ROLE)));

    public UserDetailImpl loadUser(String userName){
        if(Objects.equals(userName, USER_NAME)){
            return ONLY_USER;
        }
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(Objects.equals(userName, USER_NAME)){
            return ONLY_USER;
        }
        throw new UsernameNotFoundException(userName+" Not Found");
    }
}