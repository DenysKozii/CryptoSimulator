package com.company.crypto.service;

import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {

    void authorizeUser(User user);

    UserProfileDto getProfileOfCurrent();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
