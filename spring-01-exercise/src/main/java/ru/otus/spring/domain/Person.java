package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private int id; //идентификатор в базе данных
    private String name;
    private int age;
    public Person(String name) {
        this.name = name;
    }
}
