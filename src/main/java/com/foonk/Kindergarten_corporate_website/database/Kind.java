package com.foonk.Kindergarten_corporate_website.database;

public enum Kind {
    KTP("КТП"), WORK_PROJECTS("Рабочие проекты"), SCHEDULE("Расписание"), EVENT("Мероприятие"), INSTRUCTION ("Инструкция");
    private String name;
    Kind(String name){
        this.name = name;
    }
    public String getName(){ return name;}
}
