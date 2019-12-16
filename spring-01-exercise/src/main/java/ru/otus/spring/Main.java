package ru.otus.spring;

//import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.Examination;

public class Main {

    public static void main(String[] args) {
        // TODO: создайте здесь класс контекста

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
/*
        PersonService service = context.getBean(PersonServiceImpl.class);
        Person ivan = service.getByName("Ivan");
        System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());


        MyCsvLoader loader = context.getBean(MyCsvLoader.class);
        Resource res = loader.getRes();
        System.out.println("res exists = " + res.exists());

        try {
            InputStream is = res.getInputStream();
            try {
                InputStreamReader inR = new InputStreamReader(is);
                BufferedReader buf = new BufferedReader(inR);
                String line;
                while ((line = buf.readLine()) != null) {
                    System.out.println(line);
                }
            } finally {
                is.close();
            }
        } catch (Exception exception) {
            System.out.println(exception.fillInStackTrace());
        }
*/
        Examination ex = context.getBean(Examination.class);
        ex.start();


    }
}
