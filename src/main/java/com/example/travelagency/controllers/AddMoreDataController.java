package com.example.travelagency.controllers;

import com.example.travelagency.entities.Clients;
import com.example.travelagency.repositories.ClientsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addMoreData")
public class AddMoreDataController {

    private final ClientsRepository clientsRepository;

    @Autowired
    public AddMoreDataController(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

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

        //перезаписываем данные в БД
        String clientLogin = localClient.getClientLogin();
        clientsRepository.deleteById(clientLogin);
        clientsRepository.save(localClient);

        return "redirect:/resultsPage";
    }
}
