package ru.otus.spring.dao;

import ru.otus.spring.domain.Person;

public class PersonDaoSimple implements PersonDao {
    int defaultAge = 18;
    public Person findByName(String name) {
        return new Person(name, defaultAge);
    }

    public void setDefaultAge(int defaultAge){
        this.defaultAge = defaultAge;
    }
}
