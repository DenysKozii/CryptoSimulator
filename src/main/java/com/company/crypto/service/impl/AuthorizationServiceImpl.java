package com.company.crypto.service.impl;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.User;
import com.company.crypto.exception.EntityNotFoundException;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.ProfilePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
//    private final ProfilePageService profilePageService;

    @Override
    public void authorizeUser(User user) {
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

//        List<AssetDto> assetDtoList = profilePageService.showUserPortfolio(user.getUsername());
        return new UserProfileDto(
                user.getId(),
                user.getUsername()
//                profilePageService.userPortfolioSum(user.getUsername()),
//                user.getUsdt(),
//                profilePageService.showAllOfAssets(user.getUsername()),
//                assetDtoList
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username %s not found!", username))
        );

        return org.springframework.security.core.userdetails.User.withUsername(user.getId().toString())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())))
                .build();
    }
}
