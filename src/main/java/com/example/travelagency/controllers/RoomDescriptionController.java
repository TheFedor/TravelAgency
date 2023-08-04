package com.example.travelagency.controllers;

import com.example.travelagency.entities.HotelRooms;
import com.example.travelagency.pairClasses.IntegerIntegerPair;
import com.example.travelagency.repositories.HotelRoomsRepository;
import com.example.travelagency.repositories.RoomPhotosRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roomDescription")
public class RoomDescriptionController {

    private final HotelRoomsRepository hotelRoomsRepository;
    private final RoomPhotosRepository roomPhotosRepository;

    @Autowired
    public RoomDescriptionController(HotelRoomsRepository hotelRoomsRepository, RoomPhotosRepository roomPhotosRepository) {
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.roomPhotosRepository = roomPhotosRepository;
    }

    @GetMapping
    public String getAll(Model model, HttpSession session) {

        //получаем всю информацию о номере и сохраняем ее в модель
        Integer thisRoomCode = (Integer) session.getAttribute("roomClientCode");
        HotelRooms informationAboutThisRoom = hotelRoomsRepository.findById(thisRoomCode).orElse(null);
        model.addAttribute("thisRoom", informationAboutThisRoom);

        //получаем все фотографии этого номера и сохраняем их в модель
        List<String> thisRoomPhotos = roomPhotosRepository.findPhotosLinks(thisRoomCode);
        model.addAttribute("thisRoomPhotos", thisRoomPhotos);

        return "roomDescription";

    }

    @GetMapping("/iChooseThisRoom")
    public String selectRoom(HttpSession session){
        //добавляем в сессию информацию о выбранном номере и его цене за день и возвращаем клиента на страницу с выбором номеров
        Integer thisRoomCode = (Integer) session.getAttribute("roomClientCode");
        HotelRooms allInformationAboutThisRoom = hotelRoomsRepository.findById(thisRoomCode).orElse(null);

        if (allInformationAboutThisRoom == null)
            System.out.println("error. not room_code or not room_information");

        if (session.getAttribute("roomsAndItsPrice") != null) {
            List<IntegerIntegerPair> roomsAndItsPrice = (List) session.getAttribute("roomsAndItsPrice");
            session.removeAttribute("roomsAndItsPrice");
            roomsAndItsPrice.add(new IntegerIntegerPair(thisRoomCode, allInformationAboutThisRoom.getRoomPricePerDay()));
            session.setAttribute("roomsAndItsPrice", roomsAndItsPrice);
        } else {
            List<IntegerIntegerPair> firstRoom = new ArrayList<>();
            firstRoom.add(new IntegerIntegerPair(thisRoomCode, allInformationAboutThisRoom.getRoomPricePerDay()));
            session.setAttribute("roomsAndItsPrice", firstRoom);
        }

        return "redirect:/continueOrFinishSelectingRooms";
    }

}
