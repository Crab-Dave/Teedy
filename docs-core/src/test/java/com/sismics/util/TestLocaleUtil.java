package com.sismics.util;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class TestLocaleUtil {

    @Test
    public void testNullOrEmptyReturnsEnglish() {
        Locale l1 = LocaleUtil.getLocale(null);
        Locale l2 = LocaleUtil.getLocale("");
        assertEquals(Locale.ENGLISH, l1);
        assertEquals(Locale.ENGLISH, l2);
    }

    @Test
    public void testLanguageOnly() {
        Locale l = LocaleUtil.getLocale("fr");
        assertEquals("fr", l.getLanguage());
        assertEquals("", l.getCountry());
        assertEquals("", l.getVariant());
    }

    @Test
    public void testLanguageCountry() {
        Locale l = LocaleUtil.getLocale("fr_FR");
        assertEquals("fr", l.getLanguage());
        assertEquals("FR", l.getCountry());
        assertEquals("", l.getVariant());
    }

    @Test
    public void testLanguageCountryVariant() {
        Locale l = LocaleUtil.getLocale("zh_CN_VARIANT");
        assertEquals("zh", l.getLanguage());
        assertEquals("CN", l.getCountry());
        assertEquals("VARIANT", l.getVariant());
    }
}
