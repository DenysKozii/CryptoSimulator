package com.company.crypto.service.impl;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.entity.Asset;
import com.company.crypto.entity.User;
import com.company.crypto.repository.AssetRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.ProfilePageService;
import com.company.crypto.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfilePageServiceImpl implements ProfilePageService {
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final TransactionService transactionService;

    @Override
    public Double userMoneyShower(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getUsdt();
    }

    public List<AssetDto> showUserPortfolio(String username){
        List<Asset> asset =  assetRepository.findByUser(userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new));
        return asset.stream().filter(asset1 -> asset1.getAmount()>0)
                .map(this::convertToAssetDto).collect(Collectors.toList());
    }

    @Transactional
    public Double userPortfolioSum(String username){
        List<Asset> asset =  assetRepository.findByUser(userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new));
       return asset.stream().mapToDouble(asset1 ->
            asset1.getAmount()*transactionService.getPrice(asset1.getSymbol())
       ).sum();
    }

    public Double showAllOfAssets(String username){
        User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        return user.getUsdt()+userPortfolioSum(username);
    }

    @Transactional
    public boolean addMoneyToUser(Double money,String username){
        User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
            user.setUsdt(user.getUsdt()+money);
            userRepository.save(user);
            return true;
    }


    private AssetDto convertToAssetDto(Asset asset){
        return AssetDto.builder()
                .symbol(asset.getSymbol())
                .amount(asset.getAmount())
                .sum(asset.getAmount()* transactionService.getPrice(asset.getSymbol()))
                .build();
    }





}
