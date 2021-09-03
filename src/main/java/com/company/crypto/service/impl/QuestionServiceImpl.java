package com.company.crypto.service.impl;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.entity.Price;
import com.company.crypto.entity.Question;
import com.company.crypto.entity.Role;
import com.company.crypto.entity.User;
import com.company.crypto.mapper.PriceMapper;
import com.company.crypto.mapper.QuestionMapper;
import com.company.crypto.repository.QuestionRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.QuestionService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final AuthorizationService authorizationService;
    private final QuestionRepository questionRepository;


    @Override
    public List<QuestionDto> getAll() {
        List<Question> questions = questionRepository.findAll().stream()
                .sorted(Comparator.comparing(Question::getOrderId))
                .collect(Collectors.toList());
        return questions.stream()
                .map(QuestionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean create(Long orderId, String title, String context, Double answer, MultipartFile imageQuestion, MultipartFile imageAnswer) throws IOException {
        Question question = new Question();
        if (orderId != null){
            // Todo update
            question.setOrderId(orderId);
        } else {
            question.setOrderId(questionRepository.count());
        }
        question.setTitle(title);
        question.setContext(context);
        question.setAnswer(answer);
        if (!imageQuestion.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageQuestion.getOriginalFilename()));
            if (!fileName.isEmpty()) {
                question.setImageQuestion(Base64.getEncoder().encodeToString(imageQuestion.getBytes()));
            }
        }
        if (!imageAnswer.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageAnswer.getOriginalFilename()));
            if (!fileName.isEmpty()) {
                question.setImageAnswer(Base64.getEncoder().encodeToString(imageAnswer.getBytes()));
            }
        }
        questionRepository.save(question);
        return true;
    }
}
