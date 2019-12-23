package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.component.LocaleHolder;
import ru.otus.spring.dao.ExamQuestionsFileDao;
import ru.otus.spring.domain.ExamProperties;
import ru.otus.spring.domain.Question;

import java.util.*;

@Service
@PropertySource("application.properties")
public class ExamService {
    private final List<String> examsList;
    private final ExamQuestionsFileDao examQuestionsFileDao;
    private final LocaleHolder localeHolder;

    @Autowired
    public ExamService(ExamQuestionsFileDao examQuestionsFileDao, @Value("${exams.list}") String[] examsList, LocaleHolder localeHolder) {
        this.examQuestionsFileDao = examQuestionsFileDao;
        this.examsList = Arrays.asList(examsList);
        this.localeHolder = localeHolder;
    }

    public List<Question> getQuestionsByExamName(String examName) {

        if (!examsList.contains(examName)) {
            System.out.println("Exam " + examName + " is not available");
        } else {

            //реализовать отдельный DAO который по экзамену выведет источник вопросов + настройки источника.
            // Пока предположим что возвращается источник CSV с указанным путем до файла. Бин DAO для доставания из базы тоже пока не реализую
            // создам конфигурационный файл искуственно
            Locale l = Locale.getDefault();
            String questionPath = localeHolder.getMessage("exams.csv.path."+examName, new String[]{"Ivan"},  Locale.getDefault());
            Map<String, ExamProperties> examPropertiesMap = new HashMap<>();
            examPropertiesMap.put(examName, new ExamProperties("csv", questionPath));

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



