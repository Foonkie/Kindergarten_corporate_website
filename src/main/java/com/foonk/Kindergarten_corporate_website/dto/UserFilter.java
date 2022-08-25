package com.foonk.Kindergarten_corporate_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

/*Класс с данными для условия для использования в запросах Querydsl, представляет из себя dto.*/
@Getter
@AllArgsConstructor
@Value
public class UserFilter {
    public String firstname;
    public String lastname;
    public LocalDate birthDate;

    public String firstname(){
        return firstname;
    }
    public String lastname(){
        return lastname;
    }
    public LocalDate birthDate(){
        return birthDate;
    }
}
