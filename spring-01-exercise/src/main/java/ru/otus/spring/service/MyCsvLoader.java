package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@AllArgsConstructor
public class MyCsvLoader {
    @Getter
    private Resource res;
}
