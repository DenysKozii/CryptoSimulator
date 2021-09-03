package com.company.crypto.mapper;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDto mapToDto(Question question);
}
