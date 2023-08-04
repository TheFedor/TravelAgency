package com.example.travelagency.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/continueOrFinishSelectingRooms")
public class ContinueOrFinishSelectingRoomsController {

    @GetMapping
    public String selectThisPage() {
        return "continueOrFinishSelectingRooms";
    }

}
