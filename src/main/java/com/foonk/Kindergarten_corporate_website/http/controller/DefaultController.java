package com.foonk.Kindergarten_corporate_website.http.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class DefaultController {
    @GetMapping
    public String defaultAfterLoginMainPage(){
        return "redirect:/default";
    }
    @GetMapping("/default")
    public String defaultAfterLogin() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        String route;
        switch (role) {
            case "ADMIN":
                route="user/admin_first_page";
                break;
            case "RUSSIAN_TEACHER":
                route="redirect:/russian";
                break;
            case "ENGLISH_TEACHER":
                route="redirect:/english";
                break;
            case "OTHER_TEACHER": {
                route="redirect:/other";
                break;
            }
            default: {
                route="redirect:/login";
                break;
            }
        }
        return route;
    }
}
