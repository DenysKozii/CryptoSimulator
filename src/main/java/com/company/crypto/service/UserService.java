package com.company.crypto.service;


import com.company.crypto.dto.UserDto;

public interface UserService {

    boolean addUser(UserDto user);

    boolean addUsdt(Double usdt);

    UserDto getUserProfile();
}
