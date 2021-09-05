package com.company.crypto.service.impl;

import com.company.crypto.dto.OrderInfoDto;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.entity.Asset;
import com.company.crypto.entity.Price;
import com.company.crypto.entity.Transaction;
import com.company.crypto.entity.User;
import com.company.crypto.enums.Action;
import com.company.crypto.mapper.TransactionMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AuthorizationService authorizationService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PriceRepository priceRepository;

    private final static Double TAX = 0.00075;

    @Override
    public boolean buy(String symbol, Double usdt, Double amount) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        if (usdt > user.getUsdt()) {
            log.error(String.format("%s haven't enough usdt", username));
            return false;
        }
        Asset asset = assetRepository.findFirstByUserAndSymbol(user, symbol)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Asset with symbol %s for user %s does not exists!",
                                symbol, username)));
        Price price = priceRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                        symbol)));
        double close = price.getPrice();
        double deltaUSDT;
        double deltaAmount;
        if (usdt != 0.0) {
            deltaUSDT = -usdt + usdt * TAX;
            deltaAmount = -deltaUSDT / close;
            if (deltaAmount > price.getMinimum()) {
                user.setUsdt(user.getUsdt() - usdt);
                asset.setAmount(asset.getAmount() + deltaAmount);
            }
        } else {
            deltaAmount = amount;
            usdt = amount * close;
            deltaUSDT = -usdt - usdt * TAX;
            if (deltaAmount > price.getMinimum()) {
                user.setUsdt(user.getUsdt() + deltaUSDT);
                asset.setAmount(asset.getAmount() - deltaAmount);
            }
        }
        Transaction transaction = new Transaction();
        transaction.setSymbol(symbol);
        transaction.setPrice(close);
        transaction.setAmount(deltaAmount);
        transaction.setUsdt(usdt);
        transaction.setAction(Action.BUY);
        transaction.setUser(user);

        log.info(String.format("%s %s %s by %s on %s",
                transaction.getAction(),
                transaction.getAmount(),
                symbol,
                username,
                transaction.getUsdt()));

        assetRepository.save(asset);
        userRepository.save(user);
        transactionRepository.save(transaction);
        return true;
    }

    @Override
    public boolean sell(String symbol, Double usdt, Double amount) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        Asset asset = assetRepository.findFirstByUserAndSymbol(user, symbol)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Asset with symbol %s for user %s does not exists!",
                                symbol, username)));
        if (amount > asset.getAmount()) {
            log.info(username + " haven't enough amount of " + symbol);
            return false;
        }
        Price price = priceRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                        symbol)));
        double close = price.getPrice();

        double deltaUSDT;
        double deltaAmount;
        if (amount != 0.0) {
            deltaAmount = amount * close;
            if (amount > price.getMinimum()) {
                user.setUsdt(user.getUsdt() + deltaAmount - deltaAmount * TAX);
                asset.setAmount(asset.getAmount() - amount);
            }
        } else {
            deltaUSDT = usdt - usdt * TAX;
            deltaAmount = deltaUSDT / close;
            if (deltaAmount > price.getMinimum()) {
                user.setUsdt(user.getUsdt() + usdt);
                asset.setAmount(asset.getAmount() + deltaAmount);
            }
        }
        Transaction transaction = new Transaction();
        transaction.setSymbol(symbol);
        transaction.setPrice(close);
        transaction.setAmount(amount);
        transaction.setUsdt(deltaAmount);
        transaction.setAction(Action.SELL);
        transaction.setUser(user);

        log.info(String.format("%s %s %s by %s on %s",
                transaction.getAction(),
                transaction.getAmount(),
                symbol,
                username,
                transaction.getUsdt()));

        assetRepository.save(asset);
        userRepository.save(user);
        transactionRepository.save(transaction);
        return true;
    }

    @Override
    public boolean buyStop(String symbol, Double amount, Double stop) {
        log.info(String.format("Buy stop %s %s", symbol, stop));
        return false;
    }

    @Override
    public boolean sellStop(String symbol, Double amount, Double stop) {
        log.info(String.format("Sell stop %s %s", symbol, stop));
        return false;
    }

    @Override
    public List<TransactionDto> getAllByUser() {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        List<Transaction> transactions = transactionRepository.findAllByUser(user);
        log.info(String.format("Showed statistic on %s", username));
        return transactions.stream()
                .map(TransactionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderInfoDto getOrderInfo(String symbol) {
        log.info(String.format("Showed info on %s", symbol));
        OrderInfoDto orderInfoDto = new OrderInfoDto();

        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));

        Double price = priceRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Price with symbol %s does not exists!",
                        symbol)))
                .getPrice();

        Double amount = assetRepository.findFirstByUserAndSymbol(user, symbol)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Asset with symbol %s for user %s does not exists!",
                        symbol, username)))
                .getAmount();

        Double availableUsdt = user.getUsdt();
        orderInfoDto.setSymbol(symbol);
        orderInfoDto.setPrice(price);
        orderInfoDto.setAmount(amount);
        orderInfoDto.setAvailableUsdt(availableUsdt);
        return orderInfoDto;
    }
}
