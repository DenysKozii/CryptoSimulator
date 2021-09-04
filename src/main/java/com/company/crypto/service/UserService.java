package com.company.crypto.service;


import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.UserDto;

import java.util.List;

public interface UserService {

    boolean addUser(UserDto user);

    Double userMoneyShower(String username);

    List<AssetDto> showUserPortfolio(String username);

    Double userPortfolioSum(String username);

    Double showAllOfAssets(String username);

    boolean addUsdt(Double usdt);
}
