package com.derteuffel.archive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/archives")
public class HomeController {


    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }


}
