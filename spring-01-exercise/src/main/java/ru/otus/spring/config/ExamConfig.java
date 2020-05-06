package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExamConfig {
    String name;
    String sourceType;
    String csvQuestionSource;
    BigDecimal passPercent;
}
