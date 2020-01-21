package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.component.CsvQuestionLoader;
import ru.otus.spring.domain.Question;

import java.util.List;

/*
 * Возвращает список вопросов для конкретного экзамена из файла
 * */

@Repository
public class ExamQuestionsFileDao implements ExamQuestions {

    private final CsvQuestionLoader csvQuestionLoader;

    public ExamQuestionsFileDao (CsvQuestionLoader csvQuestionLoader) {
        this.csvQuestionLoader = csvQuestionLoader;
    }

    public List<Question> getQuestionsByExamName(String filePath) {
        List<Question> questionList = csvQuestionLoader.loadQuestionsFromFile(filePath); //сюда передвать название файла которое приходит из сервиса
        return questionList;
    }
}
