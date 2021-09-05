package com.company.crypto.controller;

import com.company.crypto.dto.QuestionDto;
import com.company.crypto.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public String create() {
        log.info("Displayed page for creating questions");
        return "questionEditor";
    }

    @GetMapping("/{id}")
    public String updateById(@PathVariable Long id, Model model){
        log.info("Displayed page for creating questions");
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("orderId",questionDto.getOrderId());
        model.addAttribute("title",questionDto.getTitle());
        model.addAttribute("context",questionDto.getContext());
        model.addAttribute("answer",questionDto.getAnswer());
        return "questionEditor";
    }

    @PostMapping("/create")
    public String addQuestion(
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
        log.info("Displayed page with question result");
        QuestionDto question = questionService.answer(orderId, answer);
        model.addAttribute("question", question);
        return "result";
    }

    @GetMapping("/list")
    public String getAllQuestions(Model model) {
        log.info("Displayed page with questions list");
        List<QuestionDto> questions = questionService.getAll();
        model.addAttribute("questions", questions);
        return "questionsList";
    }

    @GetMapping("/next")
    public String nextQuestion(Model model) {
        try {
            log.info("Displayed page with question");
            QuestionDto question = questionService.getNext();
            model.addAttribute("question", question);
            return "question";
        } catch (EntityNotFoundException e) {
            log.info("Displayed completed question page");
            return "completed";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/question/list";
    }

}
