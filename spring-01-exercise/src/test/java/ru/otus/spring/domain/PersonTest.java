package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Класс Person")
class PersonTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void testConstructorWithAsserts() {
        Person person = new Person("Ivan");
        assertThat(person.getName())
                .startsWith("Iv")
                .endsWith("an")
                .isEqualTo("Ivan")
                .hasSize("Ivan".length());
    }
}