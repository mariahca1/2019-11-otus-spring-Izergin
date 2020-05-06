package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.ExamService;

@ShellComponent
public class ShellExamApi {
    @Autowired
    ExamService examService;

    @ShellMethod(key = {"login","l"}, value = "Provides user name to application")
    public String login(@ShellOption(value = "userName") String studentName){
        examService.setStudentName(studentName);
        return String.format("Hello, %s. Now you can start an exam!",studentName);
    }

    @ShellMethod(key = {"examList","el"}, value = "Shows list of available exams")
    public String showAvailableExams(){
        return examService.getExamList().toString();
    }

    @ShellMethod(key = {"startExam","start", "s"}, value = "Starts an exam")
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public String startExam(@ShellOption(value = "examName") String examName){
        return examService.startExam(examName);
    }

    @ShellMethod(key = {"locale","loc"}, value = "Set locale ('ru' or 'en')")
    public void setLocale(@ShellOption(value = "locale") String locale){
        examService.setLocale(locale);
    }

    private Availability isUserLoggedIn(){
        return examService.getStudentName() == null ? Availability.unavailable("Сперва необходимо авторизоваться") : Availability.available();
    }
}
