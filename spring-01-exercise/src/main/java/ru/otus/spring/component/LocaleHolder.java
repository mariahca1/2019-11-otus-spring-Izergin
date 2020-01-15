package ru.otus.spring.component;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleHolder implements MessageSource{

    private ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    public LocaleHolder(){
        ms.setBasename("/bundle");
        ms.setDefaultEncoding("UTF-8");
    }

    @Override
    public String getMessage(String s, Object[] objects, String s1, Locale locale) {
        return null;
    }

    @Override
    public String getMessage(String s, Object[] objects, Locale locale) throws NoSuchMessageException {
       return ms.getMessage(s,objects, locale); //нам пока достаточно только этого одного метода
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        return null;
    }

}
