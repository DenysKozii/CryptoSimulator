package com.company.crypto.service;


import com.company.crypto.dto.UserDto;

import java.util.List;

public interface UserService {

    boolean login(String username);

    boolean addUsdt(Double usdt);

    UserDto getUserProfile();

    List<UserDto> getRatingList();
}
