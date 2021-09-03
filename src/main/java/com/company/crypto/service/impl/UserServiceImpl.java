package com.company.crypto.service.impl;

import com.company.crypto.dto.LoginRequest;
import com.company.crypto.dto.UserDto;
import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.Role;
import com.company.crypto.entity.User;
import com.company.crypto.exception.EntityNotFoundException;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean addUser(UserDto userDto) {
        Optional<User> userFromDb = userRepository.findByUsername(userDto.getUsername());
        User user;
        if (userFromDb.isPresent()) {
            user = userFromDb.get();
        } else{
            user = new User();
            user.setRole(Role.USER);
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getUsername()));
            userRepository.save(user);
        }
        authorizationService.authorizeUser(user);
        return true;
    }
}
