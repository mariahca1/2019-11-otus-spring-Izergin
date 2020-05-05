package ru.otus.spring.service;

import lombok.Getter;
import lombok.Setter;
import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.spring.component.LocaleHolder;
import ru.otus.spring.config.YMLConfig;
import ru.otus.spring.dao.ExamQuestionsFileDao;
import ru.otus.spring.domain.ExamProperties;
import ru.otus.spring.domain.Question;

import java.util.*;

@Service
@Setter
@Getter
public class ExamService {

    //будем хранить состояние - имя студента
    private String studentName;

    private final ExamQuestionsFileDao examQuestionsFileDao;
    private final LocaleHolder localeHolder;
    private final YMLConfig ymlConfig;
    private final LineReader lineReader;

    public ExamService(ExamQuestionsFileDao examQuestionsFileDao, LocaleHolder localeHolder, YMLConfig ymlConfig, @Lazy LineReader lineReader) {
        this.examQuestionsFileDao = examQuestionsFileDao;
        this.localeHolder = localeHolder;
        this.ymlConfig = ymlConfig;
        this.lineReader = lineReader;
    }

    public List<String> getExamList() {
        ArrayList<String> examList = new ArrayList<>();
        ymlConfig.getExamList().forEach(a -> examList.add(a.getName()));
        return examList;
    }

    public String startExam(String examName) {
        checkExamExists(examName);
        List<Question> qList = getQuestionsByExamName(examName);
        int rightAnswers = 0;
        for (Question question : qList) {
            if (ask(question.getQuestion()).equals(question.getTrueAnswer())) {
                rightAnswers += 1;
            }
        }
        return localeHolder.getMessage("exams.result", new String[]{studentName, rightAnswers + "", qList.size() + ""}, Locale.getDefault());
    }

    public void setLocale(String locale) {
        if (locale.equals("ru")) {
            Locale.setDefault(new Locale("ru", "RU"));
        } else if (locale.equals("en")) {
            Locale.setDefault(Locale.ENGLISH);
        } else
            throw new NoSuchElementException(String.format("Locale %s is not available", locale));
    }

    public List<Question> getQuestionsByExamName(String examName) {
        List<Question> questionList;

        String questionPath = localeHolder.getMessage("exams.csv.path." + examName, new String[]{"Ivan"}, Locale.getDefault());
        Map<String, ExamProperties> examPropertiesMap = new HashMap<>();
        examPropertiesMap.put(examName, new ExamProperties("csv", questionPath));

        if (examPropertiesMap.get(examName).getExamQuestionsSource().equals("csv")) {
            questionList = examQuestionsFileDao.getQuestionsByExamName(examPropertiesMap.get(examName).getExamQuestionsPath());
        } else {
            //Ветка создана если будет другой источнк вопросов, не CSV. Тут то же самое вернем для заглушки
            questionList = examQuestionsFileDao.getQuestionsByExamName(examPropertiesMap.get(examName).getExamQuestionsPath());
        }
        return questionList;
    }

    public void checkExamExists(String examName) {
        List<String> examList = this.getExamList();
        if (!examList.contains(examName)) {
            throw new NoSuchElementException("Exam " + examName + " is not available");
        }
    }

    private String ask(String question) {
        String answer = this.lineReader.readLine(question);
        return answer;
    }
}



