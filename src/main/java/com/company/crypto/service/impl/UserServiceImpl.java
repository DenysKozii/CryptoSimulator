package com.company.crypto.service.impl;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.entity.Asset;
import com.company.crypto.entity.Price;
import com.company.crypto.entity.Role;
import com.company.crypto.entity.User;
import com.company.crypto.enums.Symbols;
import com.company.crypto.repository.AssetRepository;
import com.company.crypto.repository.PriceRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.TransactionService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PasswordEncoder passwordEncoder;
    private final PriceRepository priceRepository;

    private final static Double START_USDT = 1000.0;

    @Override
    public boolean addUser(String username) {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        log.info("Added user with " + username + " username");
        User user;
        if (userFromDb.isPresent()) {
            user = userFromDb.get();
        } else {
            user = new User();
            user.setRole(Role.USER);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(username));
            user.setUsdt(START_USDT);
            user.setQuestionOrderId(1L);
            userRepository.save(user);
            for (Symbols symbols : Symbols.values()) {
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

    @Transactional
    public boolean addUsdt(Double usdt) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        user.setUsdt(user.getUsdt() + usdt);
        log.info("Added " + usdt + " usdt to " + username);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getUserProfile() {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        log.info("Showed " + username + " profile");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(username);
        userDto.setUsdt(user.getUsdt());

        List<Asset> assets = assetRepository.findByUser(user);
        Double assetsTotal = assets.stream()
                .mapToDouble(asset ->
                        asset.getAmount() * priceRepository.findBySymbol(asset.getSymbol())
                                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                                        asset.getSymbol())))
                                .getPrice())
                .sum();

        userDto.setAssetsTotal(assetsTotal);
        userDto.setTotal(assetsTotal + user.getUsdt());

        List<AssetDto> assetDtos = assets.stream()
                .filter(asset -> asset.getAmount() > 0)
                .map(this::convertToAssetDto)
                .sorted(Comparator.comparing(AssetDto::getSum))
                .collect(Collectors.toList());
        Collections.reverse(assetDtos);
        userDto.setAssets(assetDtos);
        return userDto;
    }

    private AssetDto convertToAssetDto(Asset asset) {
        Double price = priceRepository.findBySymbol(asset.getSymbol())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                        asset.getSymbol())))
                .getPrice();
        return AssetDto.builder()
                .symbol(asset.getSymbol())
                .amount(asset.getAmount())
                .sum(asset.getAmount() * price)
                .build();
    }
}
