package com.company.crypto.controller.rest;

import com.company.crypto.dto.AnswerDto;
import com.company.crypto.dto.QuestionDto;
import com.company.crypto.entity.User;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    private final QuestionService questionService;

    @PostMapping("/answer")
    public QuestionDto answerQuestion(@RequestBody AnswerDto answer,
                                      @AuthenticationPrincipal User user) {
        return questionService.answer(user.getUsername(), answer.getOrderId(), answer.getAnswer());
    }

    @GetMapping("/next")
    public QuestionDto nextQuestion(@AuthenticationPrincipal User user) {
        return questionService.getNext(user.getUsername());
    }

}
