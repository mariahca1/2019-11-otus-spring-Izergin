package ru.otus.spring.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Question {
    private String questionNumber;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String trueAnswer;
}
