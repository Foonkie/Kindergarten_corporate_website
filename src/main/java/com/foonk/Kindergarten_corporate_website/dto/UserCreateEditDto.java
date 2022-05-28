package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Role;
import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
@Value
@FieldNameConstants
public class UserCreateEditDto {

        @Email
        String username;

        @NotBlank(groups = CreateAction.class)
        String rawPassword;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate;

        String firstname;

        String lastname;

        Role role;

        MultipartFile image;

    }

