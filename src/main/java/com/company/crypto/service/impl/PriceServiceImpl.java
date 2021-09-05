package com.company.crypto.service.impl;

import com.company.crypto.dto.PriceDto;
import com.company.crypto.entity.Price;
import com.company.crypto.mapper.PriceMapper;
import com.company.crypto.repository.PriceRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.PriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    public List<PriceDto> getAll() {
        log.info("Showed all crypto prices");
        List<Price> priceList = priceRepository.findAll();
        return priceList.stream()
                .map(PriceMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }
}
