package com.company.crypto.service;


import com.company.crypto.dto.QuestionDto;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

public interface QuestionService {

    List<QuestionDto> getAll();

    boolean create(Long orderId, String title, String context, Double answer, MultipartFile imageQuestion, MultipartFile imageAnswer) throws IOException;

    QuestionDto getNext(String username) throws EntityNotFoundException;

    QuestionDto answer(String username, Long orderId, Double answer);

    void delete(Long id);

    QuestionDto getById(Long id);
}
