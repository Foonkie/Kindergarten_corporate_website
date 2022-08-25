package com.foonk.Kindergarten_corporate_website.database;

import org.springframework.security.core.GrantedAuthority;

/*Роль пользователя в виде enum.*/
public enum Role implements GrantedAuthority {
    ADMIN, RUSSIAN_TEACHER, ENGLISH_TEACHER, OTHER_TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
