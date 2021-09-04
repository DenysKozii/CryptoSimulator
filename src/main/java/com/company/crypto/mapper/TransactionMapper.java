package com.company.crypto.mapper;

import com.company.crypto.dto.TransactionDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.entity.Transaction;
import com.company.crypto.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto mapToDto(Transaction transaction);
}
