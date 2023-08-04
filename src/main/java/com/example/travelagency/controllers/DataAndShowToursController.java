package com.example.travelagency.controllers;

import com.example.travelagency.entities.Tours;
import com.example.travelagency.pairClasses.IntegerBooleanPair;
import com.example.travelagency.repositories.HotelRoomsRepository;
import com.example.travelagency.repositories.RoomReservationsRepository;
import com.example.travelagency.repositories.ToursRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dateAndShowTours")
public class DataAndShowToursController {

    private final ToursRepository toursRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final RoomReservationsRepository roomReservationsRepository;

    //Так как конструктор один, то @Autowired писать необязательно, так как
    //проект видит что конструктор только один и автоматически считает его
    //конструктором для внедрения зависимостей. Но, мне кажется корректнее, укажем
    @Autowired
    public DataAndShowToursController(ToursRepository toursRepository, HotelRoomsRepository hotelRoomsRepository, RoomReservationsRepository roomReservationsRepository) {
        this.toursRepository = toursRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.roomReservationsRepository = roomReservationsRepository;
    }

    @GetMapping
    public String checkClientDatesAndFindTours(Model model, HttpSession session) {

        LocalDate clientTourStartDate = (LocalDate) session.getAttribute("clientTourStartDate");
        LocalDate clientTourEndDate = (LocalDate) session.getAttribute("clientTourEndDate");

        List<Tours> toursList = toursRepository.findAll();
        model.addAttribute("toursList", toursList);

        if (clientTourStartDate == null || clientTourEndDate == null) {
            model.addAttribute("showTours", false);
            return "dateAndShowTours";
        }
        else {
            model.addAttribute("showTours", true);
        }

        //Вот тут проверяем, если все номера отеля на данную дату уже зарезервированы, то указываем для отеля серый фон
        //также сразу сохраняем доступность каждого из номеров на выбранные клиентом даты

        //создаем список, в котором будем хранить доступность всех номеров на выбранные клиентом даты
        List<IntegerBooleanPair> listNumbersAvailable = new ArrayList<>();
        //1. Получаем коды всех номеров каждого отеля
        for (Tours tour : toursList)
        {
            Integer thisTourCode = tour.getTourCode();
            List<Integer> thisHotelRooms = hotelRoomsRepository.findRoomsByTourCode(thisTourCode);
            //2. Получаем все даты начала и конца резервации для каждого номера этого тура
            for (Integer thisRoomCode : thisHotelRooms)
            {
                List<LocalDate[]> thisRoomReservationsDates = roomReservationsRepository.findRoomAllStartAndEndReservationsDates(thisRoomCode);
                //3. Проверяем, если дата пользователя попадает хоть на одну дату резервации, то этот номер мы записываем как недоступный
                boolean roomIsAvailable = true;
                for (LocalDate[] startEndRoomReservationDates : thisRoomReservationsDates)
                {
                    //проверяем даты резервации номера и даты поездки клиента
                    boolean localRoomIsAvailable = true;
                    if (!(clientTourEndDate.isBefore(startEndRoomReservationDates[0]) || clientTourStartDate.isAfter(startEndRoomReservationDates[1])))
                        localRoomIsAvailable = false;
                    if (!localRoomIsAvailable)
                        roomIsAvailable = false;
                }
                //записываем доступен ли этот номер для этого клиента в его выбранные даты
                listNumbersAvailable.add(new IntegerBooleanPair(thisRoomCode, roomIsAvailable));
            }
        }
        //сохраняем в сессию доступность для всех номеров на выбранные клиентом даты
        session.setAttribute("roomsAvailable", listNumbersAvailable);
        //создаем список для хранения доступности тура на выбранные клиентом даты (если все номера тура недоступны, то и тур недоступен)
        //      делаем это в отдельном цикле, так как не заморачиваемся со скоростью работы, а работаем на дедлайн
        List<IntegerBooleanPair> listToursAvailable = new ArrayList<>();
        for (Tours tour : toursList)
        {
            //получаем код тура
            Integer thisTourCode = tour.getTourCode();
            //создаем переменную для хранения доступности тура
            boolean tourIsAvailable = false;
            //получаем список всех номеров тура
            List<Integer> thisTourRooms = hotelRoomsRepository.findRoomsByTourCode(thisTourCode);
            //проходим по всем номерам тура
            for (Integer tourHotelsCode : thisTourRooms)
            {
                //проходим по списку доступности номеров
                for (IntegerBooleanPair roomAvailableOrNot : listNumbersAvailable)
                {
                    //если номер номера в этом списке совпадает с кодом исследуемого номера
                    if (roomAvailableOrNot.getIntegerValue().equals(tourHotelsCode))
                    {
                        //если хоть один номер тура доступен, то тур доступен
                        if (roomAvailableOrNot.isBooleanValue())
                            tourIsAvailable = true;
                    }
                }
            }
            listToursAvailable.add(new IntegerBooleanPair(thisTourCode, tourIsAvailable));
        }
        //сохраняем доступность туров в модель
        model.addAttribute("toursAvailable", listToursAvailable);

        return "dateAndShowTours";
    }

    @PostMapping
    public String savingClientTourDates(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, HttpSession session) {
        //если дата окончания поездки не позже даты начала поездки, то ставим в качестве даты окончания поездки следующий день после даты начала поездки
        if (!endDate.isAfter(startDate))
        {
            endDate = startDate.plusDays(1);
        }

        session.setAttribute("clientTourStartDate", startDate);
        session.setAttribute("clientTourEndDate", endDate);

        return "redirect:/dateAndShowTours";
    }

    @GetMapping("/selectTour/{tourCode}")
    public String selectTour(@PathVariable("tourCode") Integer tourCode, HttpSession session) {
        session.setAttribute("tourClientCode", tourCode);
        return "redirect:/tourAndTourRoomsPage";
    }

}
