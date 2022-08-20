package com.foonk.Kindergarten_corporate_website.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin() {
        return "user/admin";
    }

    @GetMapping("/admin/first_page")
    public String admin_first() {
        return "user/admin_first_page";
    }
}
