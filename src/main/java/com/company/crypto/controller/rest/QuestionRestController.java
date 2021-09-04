package com.company.crypto.controller.rest;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/answer/{orderId}")
    public QuestionDto answerQuestion(@PathVariable Long orderId,
                                      @RequestParam Double answer) {
        return questionService.answer(orderId, answer);
    }

    @GetMapping("/next")
    public QuestionDto nextQuestion() {
        return questionService.getNext();
    }

}
