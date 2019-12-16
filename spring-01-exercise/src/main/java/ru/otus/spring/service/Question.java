package ru.otus.spring.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question {
    public String questionNumber;
    public String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String trueAnswer;
}
