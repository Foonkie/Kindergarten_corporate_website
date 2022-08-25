package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Role;
import com.foonk.Kindergarten_corporate_website.database.Task;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

/*Dto для операций чтения сущности User из базы.*/
@Value
public class UserReadDto {
    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    String image;
    Role role;
}
