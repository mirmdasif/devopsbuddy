package net.asifhossain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18nService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();

        return getMessage(key, locale);
    }

    public String getMessage(String key, Locale locale) {

        return messageSource.getMessage(key, null, locale);
    }
}
