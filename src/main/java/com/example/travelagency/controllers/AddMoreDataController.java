package com.example.travelagency.controllers;

import com.example.travelagency.entities.Clients;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addMoreData")
public class AddMoreDataController {

    @GetMapping
    public String selectSeriesAndNumberBlocks(Model model) {
        model.addAttribute("localClient", new Clients());
        return "addMoreData";
    }

    @PostMapping
    public String processAddMoreDataForm(Clients client, HttpSession session)
    {
        //получаем клиента из сессии
        Clients localClient = (Clients) session.getAttribute("client");
        //дозопалняем поля этого клиента
        localClient.setPassportSeries(client.getPassportSeries());
        localClient.setPassportNumber(client.getPassportNumber());
        //возвращаем дозаполненного клиента в сессию
        session.setAttribute("client", localClient);

        return "redirect:/rezultsPage";
    }
}
