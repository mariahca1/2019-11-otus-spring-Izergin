package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.component.CsvQuestionLoader;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("Класс ExamQuestionsFileDao")
public class ExamQuestionsFileDaoTest {

    private CsvQuestionLoader loader = mock(CsvQuestionLoader.class);
    private ExamQuestions examQuestions;

    @BeforeEach
    void init() {
        examQuestions = new ExamQuestionsFileDao(loader);
    }

    @DisplayName("Возвращает список вопросов корректно")
    @Test
    void testCorrectAnswer() {
        Question q = new Question("1", "question", "ans1", "ans2", "ans3", "trueAns");
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(q);

        given(examQuestions.getQuestionsByExamName(any())).willReturn(questions);
        assertThat(examQuestions.getQuestionsByExamName(any()))
                .isNotEmpty()
                .hasSize(1)
                .contains(q)
                .containsOnly(q);
    }
}
