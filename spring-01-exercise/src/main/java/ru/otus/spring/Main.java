package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controller.ExamController;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ExamController ec = context.getBean(ExamController.class);
//        List<Question> qList = ec.getQuestionsByExamName("math");
        List<Question> qList = ec.getQuestionsByExamName("biology");
        if (qList == null) {return;}

        //познакомимся с пользователем
        Person person = new Person("Igor");

        //задать вопросы
        int rightAnswers = 0;
        for (Question e : qList) {
            rightAnswers += ask(e);
        }
        System.out.println("You (" + person.getName() + ") got " + rightAnswers + " out of " + qList.size() +" questions!");
    }

    //задача 1 вопрос пользователю возвращает 1 если ответ верный, 0 - в других случаях
    private static int ask (Question question) {
        int res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(question.getQuestion());
        try {
            String answer = br.readLine();
            if (answer.equals(question.getTrueAnswer())) {
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