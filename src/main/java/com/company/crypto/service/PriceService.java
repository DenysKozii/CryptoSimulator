package com.company.crypto.service;


import com.company.crypto.dto.PriceDto;

import java.util.List;

public interface PriceService {

    List<PriceDto> getAll();
}
