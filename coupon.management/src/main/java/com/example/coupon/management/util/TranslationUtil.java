package com.example.coupon.management.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class TranslationUtil {
    @Autowired
    private static MessageSource messageSource;
    private static final String DEFAULT_L0CALE = "en_US";
    /*
      To get translated value
    */
    public static String getTranslatedMessage(String messageId,Locale locale) {
        return messageSource.getMessage(messageId, null, Objects.nonNull(locale) ? locale : new Locale(DEFAULT_L0CALE));
    }
}
