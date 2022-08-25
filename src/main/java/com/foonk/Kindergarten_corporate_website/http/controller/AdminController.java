package com.foonk.Kindergarten_corporate_website.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*Контроллер страницы админитстратора с методами*/
@Controller
public class AdminController {
/*    Метод выбора категории для администрирования*/
    @GetMapping("/admin")
    public String admin() {
        return "user/admin";
    }
/*Метод выбора блока направления педагогов*/
    @GetMapping("/admin/first_page")
    public String admin_first() {
        return "user/admin_first_page";
    }
}
