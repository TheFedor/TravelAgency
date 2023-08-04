package com.example.travelagency.controllers;

import com.example.travelagency.entities.HotelRooms;
import com.example.travelagency.entities.Tours;
import com.example.travelagency.pairClasses.IntegerBooleanPair;
import com.example.travelagency.pairClasses.IntegerIntegerPair;
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
import java.util.Optional;

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
        //удаляем код номера из сессии, если он есть
        if (session.getAttribute("roomClientCode") != null)
            session.removeAttribute("roomClientCode");

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

        //Сохраняем в модель все номера, которые клиент уже выбрал (если такие есть). Точнее, вообще все номера тура и пометку выбрал ли этот номер клиент
        //true - клиент этот номер уже выбрал, false - не выбирал этот номер
        List<IntegerBooleanPair> alreadySelectedTheseRoom = new ArrayList<>();
        if (session.getAttribute("roomsAndItsPrice") != null) {
            List<IntegerIntegerPair> roomsAndItsPrice = (List) session.getAttribute("roomsAndItsPrice");
            for (Integer roomCode : thisTourRoomCodes) {
                boolean b = false;
                for (IntegerIntegerPair roomThatWasChosen : roomsAndItsPrice) {
                    if (roomThatWasChosen.getIntegerValue1() == roomCode)
                        b = true;
                }
                alreadySelectedTheseRoom.add(new IntegerBooleanPair(roomCode, b));
            }
        }
        else {
            for (Integer roomCode : thisTourRoomCodes){
                alreadySelectedTheseRoom.add(new IntegerBooleanPair(roomCode, false));
            }
        }
        model.addAttribute("alreadySelectedTheseRoom", alreadySelectedTheseRoom);

        return "tourDescriptionAndTourRoomsPage";
    }

    @GetMapping("/toDataAndShowTours")
    public String returnToPreviousPage(HttpSession session)
    {
        if (session.getAttribute("tourClientCode") != null)
            session.removeAttribute("tourClientCode");
        if (session.getAttribute("roomsAndItsPrice") != null)
            session.removeAttribute("roomsAndItsPrice");

        return "redirect:/dateAndShowTours";
    }

    @GetMapping("/selectRoom/{roomCode}")
    public String selectRoom(@PathVariable("roomCode") Integer roomCode, HttpSession session) {

        session.setAttribute("roomClientCode", roomCode);

        return "redirect:/roomDescription";
    }

    @GetMapping("/removeRoom/{roomCode}")
    public String removeRoom(@PathVariable("roomCode") Integer roomCode, HttpSession session) {
        List<IntegerIntegerPair> alreadySelectedTheseRoom = (List) session.getAttribute("roomsAndItsPrice");
        session.removeAttribute("roomsAndItsPrice");
        for (int i = 0; i < alreadySelectedTheseRoom.size(); ++i) {
            if (alreadySelectedTheseRoom.get(i).getIntegerValue1().equals(roomCode))
            {
                alreadySelectedTheseRoom.remove(i);
                break;
            }
        }
        session.setAttribute("roomsAndItsPrice", alreadySelectedTheseRoom);

        return "redirect:/tourDescriptionAndTourRoomsPage";
    }

    @GetMapping("/isTransportPage")
    public String isAtLeastOneNumberSelected(HttpSession session)
    {
        Optional<List<IntegerIntegerPair>> roomsAndItsPriceOpt = Optional.ofNullable((List<IntegerIntegerPair>) session.getAttribute("roomsAndItsPrice"));

        if (roomsAndItsPriceOpt.isPresent())
        {
            int count = roomsAndItsPriceOpt.get().size();
            if (count > 0)
            {
                return "redirect:/transportPage";
            }
        }
        return "redirect:/tourDescriptionAndTourRoomsPage";
    }

}
