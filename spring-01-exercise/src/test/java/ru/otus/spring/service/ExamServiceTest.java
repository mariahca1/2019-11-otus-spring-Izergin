package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.component.LocaleHolder;
import ru.otus.spring.dao.ExamQuestionsFileDao;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс ExamService")
public class ExamServiceTest {

    private final String TRUE_EXAM_NAME = "math";
    private final String WRONG_EXAM_NAME = "physics";

    private ExamQuestionsFileDao examQuestionsFileDao = mock(ExamQuestionsFileDao.class);
    private String[] examsList = {"math", "biology"};
    private LocaleHolder localeHolder = mock(LocaleHolder.class);
    private ExamService examService;

    private Question q;
    private ArrayList<Question> questions;

    @BeforeEach
    void init() {
        q = new Question("1", "question", "ans1", "ans2", "ans3", "trueAns");
        questions = new ArrayList<Question>();
        questions.add(q);

        given(examQuestionsFileDao.getQuestionsByExamName(any())).willReturn(questions);
        examService = new ExamService(examQuestionsFileDao, examsList, localeHolder);
    }

    @DisplayName("Дает возможность работы только с допустимым списком экзаменов")
    @Test
    void testWrongExamName() {
        Throwable throwable = assertThrows(NoSuchElementException.class, () -> examService.getQuestionsByExamName(WRONG_EXAM_NAME));
        assertThat("Exam " + WRONG_EXAM_NAME + " is not available").isEqualTo(throwable.getMessage());
    }

    @DisplayName("Дает возможность работы только с допустимым списком экзаменов 2")
    @Test
    void testTrueExamName() {
        assertDoesNotThrow(() -> examService.getQuestionsByExamName(TRUE_EXAM_NAME));
    }

    @DisplayName("Возвращает список вопросов")
    @Test
    void testReturnedAnswers() {
        assertThat(examService.getQuestionsByExamName(TRUE_EXAM_NAME))
                .isNotEmpty()
                .hasSize(1)
                .contains(q)
                .containsOnly(q);
    }

}
