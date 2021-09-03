package com.company.crypto.mapper;

import com.company.crypto.dto.PriceDto;
import com.company.crypto.entity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    PriceDto mapToDto(Price price);
}
