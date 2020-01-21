package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface ExamQuestions {
    //геттеров и сеттеров в сервисе и в дао быть не должно
    List<Question> getQuestionsByExamName(String examName);
}
