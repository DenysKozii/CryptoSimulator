package com.company.crypto.service;


import com.company.crypto.dto.UserDto;

public interface UserService {

    boolean addUser(String username);

    boolean addUsdt(Double usdt);

    UserDto getUserProfile();
}
