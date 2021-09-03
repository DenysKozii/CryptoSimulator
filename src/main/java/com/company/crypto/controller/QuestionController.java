package com.company.crypto.controller;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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

    @PostMapping("/answer/{orderId}")
    public String answerQuestion(
                              @PathVariable Long orderId,
                              @RequestParam Double answer,
                              Model model) {
        QuestionDto question = questionService.answer(orderId, answer);
        model.addAttribute("question", question);
        return "result";
    }

    @GetMapping("/list")
    public String getAllQuestions(Model model) {
        List<QuestionDto> questions = questionService.getAll();
        model.addAttribute("questions", questions);
        return "questionsList";
    }

    @GetMapping("/next")
    public String nextQuestion(Model model) {
        try {
            QuestionDto question = questionService.getNext();
            model.addAttribute("question", question);
            return "question";
        } catch (EntityNotFoundException e){
            return "completed";
        }

    }

}
