package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.component.CsvQuestionLoader;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ExamQuestionsFileDao")
@SpringBootTest
public class ExamQuestionsFileDaoTest {
    @Configuration
    @ComponentScan("ru.otus.spring.dao")
    static class TestConfig {
    }

    @MockBean
    private CsvQuestionLoader csvQuestionLoader;

    @Autowired
    private ExamQuestionsFileDao examQuestionsFileDao;

    @DisplayName("Возвращает список вопросов корректно")
    @Test
    void testCorrectAnswer() {
        Question q = new Question("1", "question", "ans1", "ans2", "ans3", "trueAns");
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(q);

        given(examQuestionsFileDao.getQuestionsByExamName(any())).willReturn(questions);
        assertThat(examQuestionsFileDao.getQuestionsByExamName(any()))
                .isNotEmpty()
                .hasSize(1)
                .contains(q)
                .containsOnly(q);
    }
}
