package com.foonk.Kindergarten_corporate_website.database;

import java.io.Serializable;

/*Базовый интерфейс для сущностей.*/
public interface BaseEntity<T extends Serializable> {
    T getId();

    void setId(T id);
}
