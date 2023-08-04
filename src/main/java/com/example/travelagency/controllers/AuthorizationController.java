package com.example.travelagency.controllers;

import com.example.travelagency.entities.Clients;
import com.example.travelagency.repositories.ClientsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class AuthorizationController {

    private final ClientsRepository clientsRepository;

    @Autowired
    public AuthorizationController(ClientsRepository clientsRepository)
    {
        this.clientsRepository = clientsRepository;
    }

    @RequestMapping
    public String showLoginForm() {
        return "login";
    }

    @PostMapping
    public String processLogin(@RequestParam String clientLogin, @RequestParam String clientPassword, Model model, HttpSession session) {
        Clients client = clientsRepository.findById(clientLogin).orElse(null);

        if (client == null || !client.getClientPassword().equals(clientPassword)) {
            model.addAttribute("errorMessageAuthorization", "Неверный логин или пароль");
            return "login";
        }

        session.setAttribute("client", client);

        return "redirect:dateAndShowTours";
    }

}
