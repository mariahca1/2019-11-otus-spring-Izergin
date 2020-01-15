package ru.otus.spring.component;

import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CsvQuestionLoader {

    @SneakyThrows //можно не обрабатывать эксепшены
    public List<Question> loadQuestionsFromFile(String filePath) {

        try (
                InputStream is = CsvQuestionLoader.class.getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is)
        ) {
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
            return questionsList;
        }

    }

}
