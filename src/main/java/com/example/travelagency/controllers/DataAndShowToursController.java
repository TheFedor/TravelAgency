package com.example.travelagency.controllers;

import com.example.travelagency.entities.Tours;
import com.example.travelagency.repositories.ToursRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dateAndShowTours")
public class DataAndShowToursController {

    private final ToursRepository toursRepository;

    //Так как конструктор один, то @Autowired писать необязательно, так как
    //проект видит что конструктор только один и автоматически считает его
    //конструктором для внедрения зависимостей. Но, мне кажется корректнее, укажем
    @Autowired
    public DataAndShowToursController(ToursRepository toursRepository) {
        this.toursRepository = toursRepository;
    }

    @GetMapping
    public String checkClientDatesAndShowTours(Model model, HttpSession session) {

        System.out.println(session.getAttribute("clientTourStartDate"));


        String clientTourStartDate = (String) session.getAttribute("clientTourStartDate");
        String clientTourEndDate = (String) session.getAttribute("clientTourEndDate");

        if (clientTourStartDate == null || clientTourEndDate == null) {
            model.addAttribute("showTours", false);
        }
        else {
            model.addAttribute("showTours", true);
        }
        List<Tours> toursList = toursRepository.findAll();
        model.addAttribute("toursList", toursList);

        System.out.println(model.getAttribute("showTours"));

        return "dateAndShowTours";
    }

    @PostMapping
    public String processTourDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, HttpSession session, HttpServletResponse response) {
        session.setAttribute("clientTourStartDate", startDate);
        session.setAttribute("clientTourEndDate", endDate);

        //отключаем кэширование
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "redirect:/dateAndShowTours";
    }

    @GetMapping("/selectTour/{tourCode}")
    public String selectTour(@PathVariable("tourCode") Integer tourCode, HttpSession session) {
        session.setAttribute("tourClientCode", tourCode);
        return "redirect:/tourPage";
    }



}
