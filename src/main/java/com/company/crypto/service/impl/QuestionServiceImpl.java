package com.company.crypto.service.impl;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.entity.Question;
import com.company.crypto.entity.User;
import com.company.crypto.mapper.QuestionMapper;
import com.company.crypto.repository.QuestionRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final AuthorizationService authorizationService;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    private final static Double MAX_RATE = 100.0;
    private final static Double DELIMITER = 0.7;

    @Override
    public List<QuestionDto> getAll() {
        log.info("Showed all questions");
        List<Question> questions = questionRepository.findAll().stream()
                .sorted(Comparator.comparing(Question::getOrderId))
                .collect(Collectors.toList());
        return questions.stream()
                .map(QuestionMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    private void changeOrderId(Long orderId) {
        if (orderId == questionRepository.count() + 1)
            return;
        Question question = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with order id = %s not found!", orderId)));
        changeOrderId(orderId + 1);
        log.info("Changed order id to " + orderId+1);
        question.setOrderId(orderId + 1);
        questionRepository.save(question);
    }

    @Override
    public boolean create(Long orderId, String title, String context, Double answer, MultipartFile imageQuestion, MultipartFile imageAnswer) throws IOException {
        Optional<Question> questionOptional = questionRepository.findByTitle(title);
        Question question = questionOptional.orElseGet(Question::new);
        if (!orderId.equals(question.getOrderId())) {
            if (orderId < questionRepository.count()) {
                changeOrderId(orderId);
                question.setOrderId(orderId);
            } else {
                // todo fix order id update
                question.setOrderId(questionRepository.count() + 1);
            }
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
        log.info("Created new question with title " + title);
        questionRepository.save(question);
        return true;
    }

    @Override
    public QuestionDto getNext() throws EntityNotFoundException {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        Question question = questionRepository.findByOrderId(user.getQuestionOrderId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with order id = %s not found!", user.getQuestionOrderId())));
        user.setQuestionOrderId(user.getQuestionOrderId() + 1);
        userRepository.save(user);
        log.info("Showed next question to " + username +  " with title " + question.getTitle());
        return QuestionMapper.INSTANCE.mapToDto(question);
    }

    @Override
    public QuestionDto answer(Long orderId, Double answer) {
        log.info("Answer = " + answer);
        Question question = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with order id = %s not found!", orderId)));
        QuestionDto questionDto = QuestionMapper.INSTANCE.mapToDto(question);
        if (answer.equals(question.getAnswer()))
            questionDto.setRate(MAX_RATE);
        else
            questionDto.setRate(Math.min(MAX_RATE, (question.getAnswer() / Math.abs(answer - question.getAnswer())) / DELIMITER));
        return questionDto;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleted question with id " + id);
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionDto getById(Long id) {
        log.info("Found question with id " + id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with id = %s not found!", id)));
        return QuestionMapper.INSTANCE.mapToDto(question);
    }
}
