package ru.otus.spring.service;

import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;

@AllArgsConstructor
public class Examination {
    private MyResourceLoader loader;

    public void start() {
        Resource res = loader.getRes();

        try {
            InputStream is = res.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            Map<String, String> mapping = new HashMap<>();

            mapping.put("number", "questionNumber");
            mapping.put("question", "question");
            mapping.put("answer1", "answer1");
            mapping.put("answer2", "answer2");
            mapping.put("answer3", "answer3");
            mapping.put("trueAnswer", "trueAnswer");

            HeaderColumnNameTranslateMappingStrategy<Question> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
            strategy.setType(Question.class);
            strategy.setColumnMapping(mapping);

            CsvToBean csvToBean = new CsvToBean();

            List<Question> questionsList = csvToBean.parse(strategy, isr);

            //познакомимся с пользователем
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What is your name?");
            String userName = br.readLine();
            int questionsCnt = questionsList.size();
            int rightAnswers = 0;
            for (Question e : questionsList) {
                //Начинаем задавать вопросы
                System.out.println(e.getQuestion());
                String answer = br.readLine();
                if (answer.equals(e.getTrueAnswer())) {
                    System.out.println("You are right");
                    rightAnswers++;
                } else {
                    System.out.println("You are wrong");
                }
            }
            System.out.println("You (" + userName + ") got " + rightAnswers + " out of " + questionsCnt +" questions!");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}



