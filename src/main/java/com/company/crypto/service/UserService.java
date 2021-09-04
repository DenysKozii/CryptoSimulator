package com.company.crypto.service;


import com.company.crypto.dto.LoginRequest;
import com.company.crypto.dto.UserDto;
import com.company.crypto.dto.UserProfileDto;

public interface UserService {

    boolean addUser(UserDto user);

    UserProfileDto loginUser(LoginRequest request);
}
