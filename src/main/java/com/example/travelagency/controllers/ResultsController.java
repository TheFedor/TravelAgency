package com.example.travelagency.controllers;

import com.example.travelagency.entities.*;
import com.example.travelagency.pairClasses.IntegerIntegerPair;
import com.example.travelagency.repositories.*;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/resultsPage")
public class ResultsController {

    private final OrdersRepository ordersRepository;
    private final OrderRoomsRepository orderRoomsRepository;
    private final OrderExcursionsRepository orderExcursionsRepository;
    private final TransportTypesRepository transportTypesRepository;
    private final ToursRepository toursRepository;
    private final ExcursionsRepository excursionsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;

    @Autowired
    public ResultsController(OrdersRepository ordersRepository, OrderRoomsRepository orderRoomsRepository, OrderExcursionsRepository orderExcursionsRepository,
                             TransportTypesRepository transportTypesRepository, ToursRepository toursRepository, ExcursionsRepository excursionsRepository,
                             HotelRoomsRepository hotelRoomsRepository) {
        this.ordersRepository = ordersRepository;
        this.orderRoomsRepository = orderRoomsRepository;
        this.orderExcursionsRepository = orderExcursionsRepository;
        this.transportTypesRepository = transportTypesRepository;
        this.toursRepository = toursRepository;
        this.excursionsRepository = excursionsRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
    }

    @GetMapping
    public String selectAll(Model model, HttpSession session)
    {
        LocalDate startDate = (LocalDate) session.getAttribute("clientTourStartDate");
        LocalDate endDate = (LocalDate) session.getAttribute("clientTourEndDate");
        //1. Рассчитываем и сохраняем итоговую цену
        Integer finalPrice = 0;
        //2. Добавляем цену транспорта
        Integer transportPrice = (Integer) session.getAttribute("transportPrice");
        finalPrice += transportPrice;
        //3. Добавляем сумму цен за номера, умноженную на число дней отдыха
        Period period = Period.between(startDate, endDate);
        Integer days = period.getDays() + 1;
        List<IntegerIntegerPair> roomsAndItsPriceList = (List) session.getAttribute("roomsAndItsPrice");
        Integer roomsPricePerDay = 0;
        for (IntegerIntegerPair iip : roomsAndItsPriceList)
        {
            roomsPricePerDay += iip.getIntegerValue2();
        }
        finalPrice += (roomsPricePerDay * days);
        //4. Добавляем сумму за все билеты каждой выбранной экскурсии
        Map<Integer, Integer> excursionsPricesAndTicketsMap = (Map) session.getAttribute("numberOfExcursionTickets");
        Set<Integer> keys = excursionsPricesAndTicketsMap.keySet();
        Integer excursionsSummaryPrice = 0;
        for (Integer key : keys)
        {
            Excursions excursions = excursionsRepository.findById(key).orElse(null);
            Integer excursionPrice = excursions.getExcursionPrice();
            ;excursionsSummaryPrice += (excursionPrice * excursionsPricesAndTicketsMap.get(key));
        }
        finalPrice += excursionsSummaryPrice;
        //5. Сохраняем итоговую цену в модель и сессию
        session.setAttribute("finalPrice", finalPrice);
        model.addAttribute("finalPrice", finalPrice);

        return "resultsPage";
    }

    @GetMapping("/ok")
    public String ok(HttpSession session)
    {
        //Сохраняем все данные в БД
        //1. для начала получим все данные для таблицы orders
        Orders order = new Orders();
        //1.1. Сохраняем дату начала и конца поездки
        LocalDate startDate = (LocalDate) session.getAttribute("clientTourStartDate");
        LocalDate endDate = (LocalDate) session.getAttribute("clientTourEndDate");
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        //1.2. Сохраняем логин клиента
        Clients client = (Clients) session.getAttribute("client");
        order.setClients(client);
        //1.3.Сохраняем код типа транспорта
        Integer transportTypeCode = (Integer) session.getAttribute("transportTypeCode");
        TransportTypes transportType = transportTypesRepository.findById(transportTypeCode).orElse(null);
        order.setTransportTypes(transportType);
        //1.4. Сохраняем код тура
        Integer tourCode = (Integer) session.getAttribute("tourClientCode");
        Tours tour = toursRepository.findById(tourCode).orElse(null);
        order.setTours(tour);
        //1.5. Находим итоговую цену тура
        Integer finalPrice = (Integer) session.getAttribute("finalPrice");
        order.setTotalPrice(finalPrice);
        //1.6. Сохраняем заказ в БД, не забывая сохранить его и у себя
        Orders saveOrder = ordersRepository.save(order);

        //2. Теперь заполняем таблицу номера_заказа
        List<IntegerIntegerPair> roomsAndItsPriceList = (List) session.getAttribute("roomsAndItsPrice");
        for (IntegerIntegerPair iip : roomsAndItsPriceList)
        {
            OrderRooms orderRooms = new OrderRooms();
            orderRooms.setOrders(saveOrder);
            HotelRooms hotelRooms = hotelRoomsRepository.findById(iip.getIntegerValue1()).orElse(null);
            orderRooms.setHotelRooms(hotelRooms);
            orderRoomsRepository.save(orderRooms);
        }

        //3. Теперь заполним таблицу экскурсии_заказа
        Map<Integer, Integer> excursionsPricesAndTicketsMap = (Map) session.getAttribute("numberOfExcursionTickets");
        Set<Integer> keys = excursionsPricesAndTicketsMap.keySet();
        for (Integer key : keys)
        {
            if (excursionsPricesAndTicketsMap.get(key) > 0)
            {
                OrderExcursions orderExcursions = new OrderExcursions();
                orderExcursions.setOrders(saveOrder);
                orderExcursions.setNumberOfTickets(excursionsPricesAndTicketsMap.get(key));
                Excursions excursions = excursionsRepository.findById(key).orElse(null);
                orderExcursions.setExcursions(excursions);
                orderExcursionsRepository.save(orderExcursions);
            }
        }

        //перезаписываем клиента в сессию, а остальное удаляем
        Clients thisClient = (Clients) session.getAttribute("client");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            session.removeAttribute(attributeName);
        }
        session.setAttribute("client", thisClient);

        return "redirect:/dateAndShowTours";
    }

    @GetMapping("/not")
    public String not(HttpSession session)
    {
        //перезаписываем клиента в сессию, а остальное удаляем
        Clients thisClient = (Clients) session.getAttribute("client");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            session.removeAttribute(attributeName);
        }
        session.setAttribute("client", thisClient);

        return "redirect:/dateAndShowTours";
    }

}
