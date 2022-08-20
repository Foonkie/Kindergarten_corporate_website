package com.foonk.Kindergarten_corporate_website.database;

public enum Type {
    GENERAL("Общие"), METHODOLOGICAL("Методологические"), EVENTS("Мероприятия"), BUSINESS("Бизнес");
    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
