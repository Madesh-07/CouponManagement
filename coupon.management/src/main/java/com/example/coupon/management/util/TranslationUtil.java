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

    public static String getTranslatedMessage(String messageId,String localeStr) {
        return messageSource.getMessage(messageId, null, Objects.nonNull(localeStr) ? new Locale(localeStr) : new Locale(DEFAULT_L0CALE));
    }
}
