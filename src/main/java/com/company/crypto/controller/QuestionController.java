package com.company.crypto.controller;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final AuthorizationService authorizationService;
    private final QuestionService questionService;


    @GetMapping
    public String create() {
        return "questionEditor";
    }

    @PostMapping("/create")
    public String addQuestion(
//            @Valid QuestionDto question,
                              @RequestParam Long orderId,
                              @RequestParam String title,
                              @RequestParam String context,
                              @RequestParam Double answer,
                              @RequestParam(value = "imageQuestion", required = false) MultipartFile imageQuestion,
                              @RequestParam(value = "imageAnswer", required = false) MultipartFile imageAnswer) throws IOException {
        questionService.create(orderId, title, context, answer, imageQuestion, imageAnswer);
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String getAllQuestions(Model model) {
        List<QuestionDto> questions = questionService.getAll();
        model.addAttribute("questions", questions);
        return "questionsList";
    }

}
