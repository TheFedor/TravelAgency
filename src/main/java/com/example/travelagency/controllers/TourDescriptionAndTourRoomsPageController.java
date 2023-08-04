package com.example.travelagency.controllers;

import com.example.travelagency.entities.HotelRooms;
import com.example.travelagency.entities.Tours;
import com.example.travelagency.pairClasses.IntegerBooleanPair;
import com.example.travelagency.repositories.HotelPhotosRepository;
import com.example.travelagency.repositories.HotelRoomsRepository;
import com.example.travelagency.repositories.ToursRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tourDescriptionAndTourRoomsPage")
public class TourDescriptionAndTourRoomsPageController {

    private final ToursRepository toursRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final HotelPhotosRepository hotelPhotosRepository;

    @Autowired
    public TourDescriptionAndTourRoomsPageController(ToursRepository toursRepository, HotelRoomsRepository hotelRoomsRepository, HotelPhotosRepository hotelPhotosRepository) {
        this.toursRepository = toursRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.hotelPhotosRepository = hotelPhotosRepository;
    }

    @GetMapping
    public String showInformationAboutTourAndTourRooms(Model model, HttpSession session)
    {
        //получаем код тура по которому перешел клиент
        Integer clientTourCode = (Integer) session.getAttribute("tourClientCode");
        //получаем все данные о туре, по которому перешел клиент и сохраняем их в модель
        Tours thisTour = toursRepository.findById(clientTourCode).orElse(null);

        if (thisTour == null)
            System.out.println("error. client tour not found...");
        else
            model.addAttribute("thisTour", thisTour);

        //получаем всю информацию обо всех номерах этого тура и сохраняем ее в модель
        List<Integer> thisTourRoomCodes = hotelRoomsRepository.findRoomsByTourCode(clientTourCode); //список кодов всех номеров
        List<HotelRooms> roomsList = new ArrayList<>();
        for (Integer roomCode : thisTourRoomCodes)
        {
            roomsList.add(hotelRoomsRepository.findById(roomCode).orElse(null));
        }
        model.addAttribute("roomsList", roomsList);

        //получаем ссылки на все фотографии этого отеля и сохраняем их в модель
        List<String> thisHotelPhotoLinks = hotelPhotosRepository.findPhotosLinks(clientTourCode);
        model.addAttribute("thisHotelPhotoLinks", thisHotelPhotoLinks);

        //сохраняем в модель все неактивные номера
        List<IntegerBooleanPair> roomsAvailable = (List) session.getAttribute("roomsAvailable");
        model.addAttribute("roomsAvailable", roomsAvailable);


        return "tourDescriptionAndTourRoomsPage";
    }

    @GetMapping("/toDataAndShowTours")
    public String returnToPreviousPage(HttpSession session)
    {
        if (session.getAttribute("tourClientCode") != null)
            session.removeAttribute("tourClientCode");

        return "redirect:/dateAndShowTours";
    }

    @GetMapping("/selectRoom/{roomCode}")
    public String selectTour(@PathVariable("roomCode") Integer roomCode, HttpSession session) {
        session.setAttribute("roomClientCode", roomCode);
        HotelRooms thisHotelRoom = hotelRoomsRepository.findById(roomCode).orElse(null);
        if (thisHotelRoom == null)
            System.out.println("error. room not found...");
        else
        {
            Integer thisRoomPricePerDay = thisHotelRoom.getRoomPricePerDay();
            session.setAttribute("roomPrice", thisRoomPricePerDay);
        }

        return "redirect:/roomDescription";
    }

}
