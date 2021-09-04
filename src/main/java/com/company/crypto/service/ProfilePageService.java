package com.company.crypto.service;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.ProfilePageDto;

import java.util.List;

public interface ProfilePageService {
    Double userMoneyShower(String username);

    List<AssetDto> showUserPortfolio(String username);

    Double userPortfolioSum(String username);

    Double showAllOfAssets(String username);

    boolean addMoneyToUser(Double money, String username);
}
