package com.example.travelagency.controllers;

import com.example.travelagency.classOfFore.ClassOfFore;
import com.example.travelagency.entities.TransportTypes;
import com.example.travelagency.pairClasses.IntegerIntegerPair;
import com.example.travelagency.repositories.TransportRepository;
import com.example.travelagency.repositories.TransportTypesRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transportPage")
public class TransportController {

    private final TransportRepository transportRepository;
    private final TransportTypesRepository transportTypesRepository;

    @Autowired
    public TransportController(TransportRepository transportRepository, TransportTypesRepository transportTypesRepository) {
        this.transportRepository = transportRepository;
        this.transportTypesRepository = transportTypesRepository;
    }

    @GetMapping
    public String selectAllTourTransport(Model model, HttpSession session) {
        Integer tourClientCode = (Integer) session.getAttribute("tourClientCode");

        //глупое решение, но другого второпях придумать не успел
        List<TransportTypes> transportTypesList = transportTypesRepository.findAll();

        //создаем список, в котором будем хранить <тип транспорта, описание транспорта, цену транспорта>
        List<ClassOfFore> allAboutTransports = new ArrayList<>();

        for (TransportTypes tt : transportTypesList)
        {
            List<Integer> pr = transportRepository.findTransportPrice(tt.getTransportTypeCode(), tourClientCode);
            if (pr != null && pr.size() < 2)
            {
                allAboutTransports.add(new ClassOfFore(tt.getTransportTypeCode(),tt.getTransportType(), tt.getTransportDescription(), pr.get(0)));
            }
        }

        //добавляем всю информацию обо всех транспортах в модель
        model.addAttribute("allAboutTransports", allAboutTransports);

        return "transportPage";
    }

    @GetMapping("selectTransport/{transportTypeCode}/{transportPrice}")
    public String selectTransport(@PathVariable("transportTypeCode") Integer transportTypeCode, @PathVariable("transportPrice") Integer transportPrice, HttpSession session)
    {
        session.setAttribute("transportTypeCode", transportTypeCode);
        session.setAttribute("transportPrice", transportPrice);

        return "redirect:/excursionsPage";
    }

}
