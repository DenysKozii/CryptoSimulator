package com.company.crypto.service.impl;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.LoginRequest;
import com.company.crypto.dto.UserDto;
import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.Asset;
import com.company.crypto.entity.Role;
import com.company.crypto.entity.User;
import com.company.crypto.enums.Symbols;
import com.company.crypto.exception.EntityNotFoundException;
import com.company.crypto.mapper.UserMapper;
import com.company.crypto.repository.AssetRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.ProfilePageService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProfilePageService profilePageService;


    private final static Double START_USDT = 1000.0;

    @Override
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
            user.setUsdt(START_USDT);
            user.setQuestionOrderId(1L);
            userRepository.save(user);
            for (Symbols symbols: Symbols.values()) {
                Asset asset = new Asset();
                asset.setAmount(0.0);
                asset.setSymbol(symbols.name());
                asset.setUser(user);
                assetRepository.save(asset);
            }
        }
        authorizationService.authorizeUser(user);
        return true;
    }

    @Override
    public UserProfileDto loginUser(LoginRequest request) {
        String username = request.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Invalid Credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("Invalid Credentials");
        }

        List<AssetDto> assetDtoList = profilePageService.showUserPortfolio(username);
        authorizationService.authorizeUser(user);
        return new UserProfileDto(
                user.getId(),
                user.getUsername(),
                profilePageService.userPortfolioSum(username),
                user.getUsdt(),
                profilePageService.showAllOfAssets(username),
                assetDtoList
        );
    }
}
