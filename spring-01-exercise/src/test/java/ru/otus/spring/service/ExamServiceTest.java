package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.dao.ExamQuestionsFileDao;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ExamService")
@SpringBootTest
@ActiveProfiles("test")
public class ExamServiceTest {

    private final String TRUE_EXAM_NAME = "testMath";
    private final String WRONG_EXAM_NAME = "physics";
    private final Question QUESTION = new Question("1", "question", "ans1", "ans2", "ans3", "ans1");

    @MockBean
    private ExamQuestionsFileDao examQuestionsFileDao;

    @Autowired
    private ExamService examService;

    @DisplayName("Дает возможность работы только с допустимым списком экзаменов")
    @Test
    void testWrongExamName() {
        Throwable throwable = assertThrows(NoSuchElementException.class, () -> examService.checkExamExists(WRONG_EXAM_NAME));
        assertThat("Exam " + WRONG_EXAM_NAME + " is not available").isEqualTo(throwable.getMessage());
    }

    @DisplayName("Дает возможность работы только с допустимым списком экзаменов 2")
    @Test
    void testTrueExamName() {
        assertDoesNotThrow(() -> examService.checkExamExists(TRUE_EXAM_NAME));
    }

    @DisplayName("Возвращает список вопросов")
    @Test
    void testReturnedAnswers() {
        ArrayList<Question> questions;
        questions = new ArrayList<Question>();
        questions.add(QUESTION);
        given(examQuestionsFileDao.getQuestionsByExamName(any())).willReturn(questions);

        assertThat(examService.getQuestionsByExamName(TRUE_EXAM_NAME))
                .isNotEmpty()
                .hasSize(1)
                .contains(QUESTION)
                .containsOnly(QUESTION);
    }

}
