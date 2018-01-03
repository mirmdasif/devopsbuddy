package net.asifhossain.devopsbuddy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18nService {

    private static final Logger LOG = LoggerFactory.getLogger(I18nService.class);

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(key, locale);
    }

    public String getMessage(String key, Locale locale) {
        LOG.info("Returning i18n text for {}", key);
        return messageSource.getMessage(key, null, locale);
    }
}
