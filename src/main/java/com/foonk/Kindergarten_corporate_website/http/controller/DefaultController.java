package com.foonk.Kindergarten_corporate_website.http.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*Контроллер для первой страницы по результатам авторизации.*/
@Controller
public class DefaultController {
/*Метод направляет на контроллер выбора экрана пользователя*/
    @GetMapping
    public String defaultAfterLoginMainPage() {
        return "redirect:/default";
    }
/*Метод для первой страницы по результатам авторизации*/
    @GetMapping("/default")
    public String defaultAfterLogin() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        String route;
        switch (role) {
            case "ADMIN":
                route = "user/admin_first_page";
                break;
            case "RUSSIAN_TEACHER":
                route = "redirect:/russian";
                break;
            case "ENGLISH_TEACHER":
                route = "redirect:/english";
                break;
            case "OTHER_TEACHER": {
                route = "redirect:/other";
                break;
            }
            default: {
                route = "redirect:/login";
                break;
            }
        }
        return route;
    }
}
