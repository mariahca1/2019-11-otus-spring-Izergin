package ru.otus.spring.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import ru.otus.spring.component.LocaleHolder;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ExamService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
public class ExamController {
    private final ExamService examService;
    private final LocaleHolder localeHolder;

    public ExamController(ExamService examService, LocaleHolder localeHolder) {
        this.examService = examService;
        this.localeHolder = localeHolder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        Person person;
        person = new Person("Igor", 18);
        person.birthday();

        askStudentForLocale();

        System.out.println(localeHolder.getMessage("hello.user", new String[]{person.getName()}, Locale.getDefault()));

        List<Question> qList;
        try {
//            qList = ec.getQuestionsByExamName("math");
            qList = examService.getQuestionsByExamName("biology");
        } catch (NoSuchElementException e)
        {
            System.out.println("Данный экзамен не поддерживается");
            System.out.println(localeHolder.getMessage ("main.examNotSupported", new String[]{}, Locale.getDefault()));
            return;
        }
        if (qList == null) {
            return;
        }

        //задать вопросы
        int rightAnswers = 0;
        for (Question e : qList) {
            rightAnswers += ask(e);
        }
        System.out.println(localeHolder.getMessage("exams.result", new String[]{person.getName(), rightAnswers + "", qList.size() + ""}, Locale.getDefault()));
    }

    //при запуске приложения спрашиваем студента на каком языке он говорит
    private void askStudentForLocale() {
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
    private int ask(Question question) {
        int res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(question.getQuestion());
        try {
            String answer = br.readLine();
            if (answer.equals(question.getTrueAnswer())) {
                System.out.println(localeHolder.getMessage("exams.answer.right", null, Locale.getDefault()));
                res = 1;
            } else {
                System.out.println(localeHolder.getMessage("exams.answer.wrong", null, Locale.getDefault()));
                res = 0;
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            res = 0;
        }
        return res;
    }
}
