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
@RequestMapping("/registration")
public class RegistrationController {

    private  final ClientsRepository clientsRepository;

    @Autowired
    public RegistrationController(ClientsRepository clientRepository)
    {
        this.clientsRepository = clientRepository;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Clients());
        return "registration";
    }

    @PostMapping
    public String processRegistrationForm(Clients client, Model model, HttpSession session) {

        //Проверяем, существует ли пользователь с таким же логином
        Clients existingClientWithLogin = clientsRepository.findById(client.getClientLogin()).orElse(null);
        if (existingClientWithLogin != null) {
            model.addAttribute("errorMessageRegistration", "Пользователь с таким логином уже зарегистрирован");
            return "registration";
        }

        // Проверяем, существует ли пользователь с таким же адресом электронной почты
        Clients existingClientWithEmail = clientsRepository.findByEmailAddress(client.getEmailAddress());
        if (existingClientWithEmail != null) {
            model.addAttribute("errorMessageRegistration", "Пользователь с таким адресом эл. почты уже был зарегистрирован");
            return "registration";
        }

        //сохраняем нашего клиента в сессии, чтобы не потерялся
        session.setAttribute("client", client);

        //переходим на новый адрес, не забывая передать сообщения модели
        return "redirect:/dateAndShowTours";

    }

}
