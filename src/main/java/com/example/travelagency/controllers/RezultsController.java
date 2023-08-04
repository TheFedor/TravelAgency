package com.example.travelagency.controllers;

import com.example.travelagency.entities.Clients;
import com.example.travelagency.pairClasses.IntegerIntegerPair;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/rezultsPage")
public class RezultsController {

    @GetMapping
    public String selectAll(Model model, HttpSession session)
    {
        System.out.println("Дата начала тура:");
        System.out.println((LocalDate) session.getAttribute("clientTourStartDate"));
        System.out.println("Дата окончания тура:");
        System.out.println((LocalDate) session.getAttribute("clientTourEndDate"));
        System.out.println("КодТура:");
        System.out.println((Integer) session.getAttribute("tourClientCode"));
        System.out.println("Коды и цены номеров:");
        List<IntegerIntegerPair> roomsList = (List) session.getAttribute("roomsAndItsPrice");
        for (IntegerIntegerPair iip : roomsList)
            System.out.println(" - код номера: " + iip.getIntegerValue1() + " ; цена номера: " + iip.getIntegerValue2());
        System.out.println("Код типа транспорта:");
        System.out.println((Integer) session.getAttribute("transportTypeCode"));
        System.out.println("Цена транспорта:");
        System.out.println((Integer) session.getAttribute("transportPrice"));
        System.out.println("Код экскурсии и число билетов на нее:");
        Map<Integer, Integer> exMap = (Map) session.getAttribute("numberOfExcursionTickets");
        Set<Integer> keys = exMap.keySet();
        for (Integer key : keys)
        {
            System.out.println(" - код экскурсии: " + key + " ; число билетов: " + exMap.get(key));
        }
        System.out.println("Данные клиента");
        System.out.println((Clients) session.getAttribute("client"));


        return "rezultsPage";
    }

}
