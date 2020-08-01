package com.wanshare.crush.setting.model.bean;

/**
 * Created by Jason on 2018/9/17.
 */

public class LanguageBean {

    private String language;

    private boolean choseLanguage;

    public LanguageBean(String language, boolean choseLanguage) {
        this.language = language;
        this.choseLanguage = choseLanguage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isChoseLanguage() {
        return choseLanguage;
    }

    public void setChoseLanguage(boolean choseLanguage) {
        this.choseLanguage = choseLanguage;
    }
}
