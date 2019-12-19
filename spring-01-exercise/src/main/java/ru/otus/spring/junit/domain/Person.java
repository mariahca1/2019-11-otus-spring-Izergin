package ru.otus.spring.junit.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String name;
    public Person(String name) {
        this.name = name;
    }
}
