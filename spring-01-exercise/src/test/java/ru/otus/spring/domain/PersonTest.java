package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс Person")
class PersonTest {

    private final String DEFAULT_NAME = "Ivan";
    private final int DEFAULT_AGE = 18;

    @DisplayName("корректно создаётся конструктором")
    @Test
    void testConstructor() {
        Person person = new Person(DEFAULT_NAME, DEFAULT_AGE);
        assertThat(person.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(person.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @DisplayName("имеет корректные setter and getter")
    @Test
    void testSetterAndGetter() {
        Person person = new Person(DEFAULT_NAME, DEFAULT_AGE);
        String newName = "NewName";
        int newAge = 33;
        assertThat(person.setName(newName).getName()).isEqualTo(newName);
        assertThat(person.setAge(newAge).getAge()).isEqualTo(newAge);
    }

    @DisplayName("имеет корректный метод birthday")
    @Test
    void testBirthday() {
        Person person = new Person(DEFAULT_NAME, DEFAULT_AGE);
        assertThat(person.birthday().getAge())
                .isGreaterThan(DEFAULT_AGE)
                .isEqualTo(DEFAULT_AGE + 1);
    }

    @DisplayName("не может быть более 100 лет при создании")
    @Test
    void testAge() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> new Person(DEFAULT_NAME, 112));
        assertThat("Too old").isEqualTo(throwable.getMessage());
    }

    @DisplayName("не может быть более 100 лет даже если доживет")
    @Test
    void testAgeAfterBirthday() {
        Person person = new Person(DEFAULT_NAME, 100);
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> person.birthday());
        assertThat("Too old").isEqualTo(throwable.getMessage());
    }
}