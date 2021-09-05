package com.company.crypto.service.impl;

import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.User;
import com.company.crypto.exception.EntityNotFoundException;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;

    @Override
    public void authorizeUser(User user) {
        log.info("User with " + user.getUsername() + " authorized");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getId(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Override
    public UserProfileDto getProfileOfCurrent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(principal instanceof UserDetails ?
                ((UserDetails)principal).getUsername() : principal.toString());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        log.info("Current user with id " + userId);
        return new UserProfileDto(
                user.getId(),
                user.getUsername()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loaded user with username " + username);
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username %s not found!", username))
        );

        return org.springframework.security.core.userdetails.User.withUsername(user.getId().toString())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())))
                .build();
    }
}
