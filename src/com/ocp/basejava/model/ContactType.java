package com.ocp.basejava.model;

public enum ContactType {
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    HOME_PHONE("Дом. телефон"),
    SKYPE("Skype"),
    MAIL("E-Mail"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль CitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
