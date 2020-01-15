package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.component.LocaleHolder;
import ru.otus.spring.controller.ExamController;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@ComponentScan
@Configuration
public class Main {
    private static LocaleHolder lh;
    private static Person person;

    public static void main(String[] args) {
        //познакомимся с пользователем
        person = new Person("Igor");

        askStudentForLocale();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        lh = context.getBean(LocaleHolder.class);

        System.out.println(lh.getMessage("hello.user", new String[]{person.getName()}, Locale.getDefault()));

        ExamController ec = context.getBean(ExamController.class);
        List<Question> qList = ec.getQuestionsByExamName("math");
        //List<Question> qList = ec.getQuestionsByExamName("biology");
        if (qList == null) {
            return;
        }

        //задать вопросы
        int rightAnswers = 0;
        for (Question e : qList) {
            rightAnswers += ask(e);
        }
        System.out.println(lh.getMessage("exams.result", new String[]{person.getName(), rightAnswers + "", qList.size() + ""}, Locale.getDefault()));
    }

    //при запуске приложения спрашиваем студента на каком языке он говорит
    private static void askStudentForLocale() {
        boolean defined = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!defined) {
            System.out.println("What language do you speak? Russian(ru) or English(en)?");
            try {
                String lang = br.readLine();
                if (lang.equals("ru")) {
                    Locale.setDefault(new Locale("ru", "RU"));
                    defined = true;
                } else if (lang.equals("en")) {
                    Locale.setDefault(Locale.ENGLISH);
                    defined = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //задаем 1 вопрос пользователю, возвращает 1 если ответ верный, 0 - в других случаях
    private static int ask(Question question) {
        int res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(question.getQuestion());
        try {
            String answer = br.readLine();
            if (answer.equals(question.getTrueAnswer())) {
                //System.out.println("You are right");
                System.out.println(lh.getMessage("exams.answer.right", null, Locale.getDefault()));
                res = 1;
            } else {
                //System.out.println("You are wrong");
                System.out.println(lh.getMessage("exams.answer.wrong", null, Locale.getDefault()));
                res = 0;
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            res = 0;
        }
        return res;
    }

}