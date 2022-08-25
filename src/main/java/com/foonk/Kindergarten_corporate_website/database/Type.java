package com.foonk.Kindergarten_corporate_website.database;

/*Тип задачи в виде enum.*/
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
