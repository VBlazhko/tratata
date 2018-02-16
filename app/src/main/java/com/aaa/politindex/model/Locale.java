package com.aaa.politindex.model;

/**
 * Created by 11 on 16.02.2018.
 */

public class Locale {
    private String localeUpper;
    private String localeLower;
    private String localeKey;
    private boolean isLocale;

    public boolean isLocale() {
        return isLocale;
    }

    public void setLocale(boolean locale) {
        isLocale = locale;
    }

    public String getLocaleUpper() {
        return localeUpper;
    }

    public void setLocaleUpper(String localeUpper) {
        this.localeUpper = localeUpper;
    }

    public String getLocaleLower() {
        return localeLower;
    }

    public void setLocaleLower(String localeLower) {
        this.localeLower = localeLower;
    }


    public String getLocaleKey() {
        return localeKey;
    }

    public void setLocaleKey(String localeKey) {
        this.localeKey = localeKey;
    }

}
