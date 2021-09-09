package com.company.crypto.service.impl;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.entity.*;
import com.company.crypto.enums.Action;
import com.company.crypto.enums.Symbols;
import com.company.crypto.mapper.UserMapper;
import com.company.crypto.repository.AssetRepository;
import com.company.crypto.repository.PriceRepository;
import com.company.crypto.repository.TransactionRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@EnableScheduling
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PriceRepository priceRepository;
    private final TransactionRepository transactionRepository;

    private final static Double START_USDT = 1000.0;
    private final static Double TAX = 0.00075;

    @Override
    public boolean login(String username) {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        log.info(String.format("Added user with %s username", username));
        User user;
        if (userFromDb.isPresent()) {
            user = userFromDb.get();
        } else {
            user = new User();
            user.setRole(Role.USER);
            user.setUsername(username);
            user.setPassword(username);
            user.setUsdt(START_USDT);
            user.setQuestionOrderId(1L);
            user.setPnl(0.0);
            user.setPnlUpdates(0);
            userRepository.save(user);
            for (Symbols symbols : Symbols.values()) {
                Asset asset = new Asset();
                asset.setAmount(0.0);
                asset.setSymbol(symbols.name());
                asset.setUser(user);
                assetRepository.save(asset);
            }
        }
        return true;
    }

    @Transactional
    public boolean addUsdt(Double usdt, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        user.setUsdt(user.getUsdt() + usdt);
        log.info(String.format("Added %s usdt to %s", usdt, username));
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getUserProfile(String username) {
        log.info(String.format("Show %s profile", username));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(username);
        userDto.setUsdt(user.getUsdt());
        userDto.setPnl(user.getPnl());

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

    @Override
    public List<UserDto> getRatingList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::mapToDto)
                .sorted(Comparator.comparing(UserDto::getPnl))
                .collect(Collectors.toList());
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

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exists!"));
    }


    @Scheduled(fixedRate = 60 * 1000)
    public void calculatePL() {
        double closeSum = 0.0;
        double openSum = 0.0;
        double plSum = 0.0;

        List<User> users = userRepository.findAll();
        for (User user : users) {

            for (Symbols symbol : Symbols.values()) {
                List<Transaction> transactions = transactionRepository.findAllByUserAndSymbolAndAnalysedFalse(user, symbol.name());
                for (Transaction transaction : transactions) {
                    if (Action.BUY.equals(transaction.getAction())) {
                        openSum += transaction.getUsdt();
                    } else {
                        closeSum += transaction.getUsdt();
                    }
                    transaction.setAnalysed(true);
                    transactionRepository.save(transaction);
                }
                Asset asset = assetRepository.findFirstByUserAndSymbol(user, symbol.name())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Asset for %s with symbol %s doesn't exists!",
                                user.getUsername(), symbol.name())));
                if (asset.getAmount() > 0) {
                    Price price = priceRepository.findBySymbol(symbol.name())
                            .orElseThrow(() -> new EntityNotFoundException(String.format("Price for %s with symbol %s doesn't exists!",
                                    user.getUsername(), symbol.name())));
                    closeSum += asset.getAmount() * price.getPrice();
                }
                plSum += closeSum - openSum - TAX * (closeSum + openSum);
            }
            user.setPnl((user.getPnl() * user.getPnlUpdates() + plSum) / (user.getPnlUpdates() + 1));
            user.setPnlUpdates(user.getPnlUpdates() + 1);
            userRepository.save(user);
        }
    }
}
