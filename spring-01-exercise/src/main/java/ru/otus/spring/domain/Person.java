package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class Person {
    private String name;
    private int age;
    private LocalDateTime localDateTime;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        if (age > 100) {
            throw new IllegalArgumentException("Too old");
        }
    }

    public Person birthday() {
        this.age = age + 1;
        if (age > 100) {
            throw new IllegalArgumentException("Too old");
        }
        return this;
    }
}
