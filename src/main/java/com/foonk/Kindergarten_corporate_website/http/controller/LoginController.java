package com.foonk.Kindergarten_corporate_website.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/*Контроллер для страницы авторизации*/
@Controller
public class LoginController {
/*Метод врзращающий страницу авторизации*/
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

}
