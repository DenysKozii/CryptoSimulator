package com.company.crypto.service.impl;

import com.company.crypto.entity.User;
import com.company.crypto.repository.AssetRepository;
import com.company.crypto.repository.PriceRepository;
import com.company.crypto.repository.TransactionRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AuthorizationService authorizationService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PriceRepository priceRepository;

    @Override
    public Double getAvailable() {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        return user.getUsdt();
    }

    @Override
    public Double getCurrentAsset(String symbol) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        return assetRepository.findFirstByUserAndSymbol(user, symbol)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Asset with symbol %s for user %s does not exists!",
                                symbol, username))).getAmount();
    }

    @Override
    public Double getPrice(String symbol) {
        return priceRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                        symbol))).getPrice();
    }
}
