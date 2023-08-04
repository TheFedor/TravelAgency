package com.example.travelagency.controllers;

import com.example.travelagency.entities.Excursions;
import com.example.travelagency.pairClasses.ExcursionsPhotos;
import com.example.travelagency.repositories.ExcursionPhotosRepository;
import com.example.travelagency.repositories.ExcursionsDatesRepository;
import com.example.travelagency.repositories.ExcursionsRepository;
import com.example.travelagency.tripleClass.TripleClass;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excursionInformationPage")
public class ExcursionInformationController {

    private final ExcursionsDatesRepository excursionsDatesRepository;
    private final ExcursionsRepository excursionsRepository;
    private final ExcursionPhotosRepository excursionPhotosRepository;

    @Autowired
    public ExcursionInformationController(ExcursionsDatesRepository excursionsDatesRepository, ExcursionsRepository excursionsRepository, ExcursionPhotosRepository excursionPhotosRepository) {
        this.excursionsDatesRepository = excursionsDatesRepository;
        this.excursionsRepository = excursionsRepository;
        this.excursionPhotosRepository = excursionPhotosRepository;
    }

    @GetMapping
    public String showExcursionInformation(Model model, HttpSession session)
    {
        //получаем код текущей экскурсии
        Integer thisExcursionCode = (Integer) session.getAttribute("excursionThatShowInformation");

        //получаем информацию о текущей экскурсии
        Excursions thisExcursion = excursionsRepository.findById(thisExcursionCode).orElse(null);

        //получаем информацию о датах проведения данной экскурсии
        List<LocalDate[]> excursionDates = excursionsDatesRepository.findAllExcursionDates(thisExcursionCode);
        for (LocalDate[] ld : excursionDates)
            System.out.println(ld[0] + " --- " + ld[1]);

        //получаем фотографии данной экскурсии
        List<String> excursionPhotos = excursionPhotosRepository.findAllExcursionPhotos(thisExcursionCode);

        //сохраняем всю полученную информацию в модель
        model.addAttribute("thisExcursion", thisExcursion);
        model.addAttribute("excursionDates", excursionDates);
        model.addAttribute("excursionPhotos", excursionPhotos);

        return "excursionInformationPage";

    }

}
