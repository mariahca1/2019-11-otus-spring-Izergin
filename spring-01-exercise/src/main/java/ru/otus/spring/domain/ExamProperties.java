package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExamProperties {
    private String examQuestionsSource;
    private String examQuestionsPath;
}
