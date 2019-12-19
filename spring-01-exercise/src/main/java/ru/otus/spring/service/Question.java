package ru.otus.spring.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    public int ask() {
        int res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(question);
        try {
            String answer = br.readLine();
            if (answer.equals(trueAnswer)) {
                System.out.println("You are right");
                res = 1;
            } else {
                System.out.println("You are wrong");
                res = 0;
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            res = 0;
        }
        return res;
    }
}
