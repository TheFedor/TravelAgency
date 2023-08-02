package com.example.travelagency.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tourPage")
public class TourPageController {

    @GetMapping
    public String showTourPage(Model model, HttpSession session) {
        Integer tourClientCode = (Integer) session.getAttribute("tourClientCode");
        System.out.println("--:  " + session.getAttribute("tourClientCode"));
        model.addAttribute("tourClientCode", tourClientCode);
        return "tourPage";
    }

}
