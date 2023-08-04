package com.example.travelagency.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/start")
public class StartController {

    @GetMapping
    public String deleteAllINformationsFromSession(HttpSession session) {
        session.invalidate();
        return "redirect:/dateAndShowTours";
    }

}
