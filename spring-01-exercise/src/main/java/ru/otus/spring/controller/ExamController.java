package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ExamService;

import java.util.List;

@Controller
public class ExamController {
    private final ExamService examService;

    //Вопросы пользователю задавать можно в слое контроллера
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    public List<Question> getQuestionsByExamName(String examName){
        return examService.getQuestionsByExamName(examName);
    }
}
