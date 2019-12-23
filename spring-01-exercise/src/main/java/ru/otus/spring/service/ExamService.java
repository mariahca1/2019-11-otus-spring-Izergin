package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.ExamQuestionsFileDao;
import ru.otus.spring.domain.ExamProperties;
import ru.otus.spring.domain.Question;

import java.util.*;

@Service
@PropertySource("application.properties")
public class ExamService {
    private final List<String> examsList;
    private final ExamQuestionsFileDao examQuestionsFileDao;

    @Autowired
    public ExamService(ExamQuestionsFileDao examQuestionsFileDao, @Value("${exams.list}") String[] examsList) {
        this.examQuestionsFileDao = examQuestionsFileDao;
        this.examsList = Arrays.asList(examsList);
    }

    public List<Question> getQuestionsByExamName(String examName) {

        if (!examsList.contains(examName)) {
            System.out.println("Exam " + examName + " is not available");
        } else {

            //реализовать отдельный DAO который по экзамену выведет источник вопросов + настройки источника.
            // Пока предположим что возвращается источник CSV с указанным путем до файла. Бин DAO для доставания из базы тоже пока не реализую
            // создам конфигурационный файл искуственно
            Map<String, ExamProperties> examPropertiesMap = new HashMap<>();
            examPropertiesMap.put("math", new ExamProperties("csv", "/questionsMath.csv"));
            examPropertiesMap.put("biology", new ExamProperties("csv", "/questionsBiology.csv"));

            if (examPropertiesMap.get(examName).getExamQuestionsSource().equals("csv")) {
                return examQuestionsFileDao.getQuestionsByFilePath(examPropertiesMap.get(examName).getExamQuestionsPath());
            } else {
                //ладно, тут то же самое вернем для заглушки
                return examQuestionsFileDao.getQuestionsByFilePath(examPropertiesMap.get(examName).getExamQuestionsPath());
            }
        }
        return null;
    }
}



