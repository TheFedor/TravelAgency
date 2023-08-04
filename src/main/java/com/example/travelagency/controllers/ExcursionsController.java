package com.example.travelagency.controllers;

import com.example.travelagency.entities.Clients;
import com.example.travelagency.entities.Excursions;
import com.example.travelagency.pairClasses.ExcursionsPhotos;
import com.example.travelagency.repositories.ExcursionPhotosRepository;
import com.example.travelagency.repositories.ExcursionsDatesRepository;
import com.example.travelagency.repositories.ExcursionsRepository;
import com.example.travelagency.tripleClass.TripleClass;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/excursionsPage")
public class ExcursionsController {

    private final ExcursionsDatesRepository excursionsDatesRepository;
    private final ExcursionsRepository excursionsRepository;
    private final ExcursionPhotosRepository excursionPhotosRepository;

    @Autowired
    public ExcursionsController(ExcursionsDatesRepository excursionsDatesRepository, ExcursionsRepository excursionsRepository, ExcursionPhotosRepository excursionPhotosRepository) {
        this.excursionsDatesRepository = excursionsDatesRepository;
        this.excursionsRepository = excursionsRepository;
        this.excursionPhotosRepository = excursionPhotosRepository;
    }

    @GetMapping
    public String selectPage(Model model, HttpSession session)
    {
        //получаем код текущего тура
        Integer thisTourCode = (Integer) session.getAttribute("tourClientCode");
        //получаем список всех кодов экскурсий тура
        List<Integer> allTourExcursionCodes = excursionsRepository.findAllTourExcursionCodes(thisTourCode);

        //для каждой экскурсии из этого списка находим даты начала и конца экскурсии
        //и сохраняем в список код экскурсии и ее доступность в рамках выбранных клиентом дат
        //(если дата поездки клиента имеет хоть один общий день с датами экскурсии, то экскурсия доступна)

        //получим даты поезки клиента
        LocalDate startClientDate = (LocalDate) session.getAttribute("clientTourStartDate");
        LocalDate endClientDate = (LocalDate) session.getAttribute("clientTourEndDate");

        //создадим список <<даты начал и концов экскурсий>, кодЭкскурсс, доступностьЭкскурсии>
        //true - доступна, false - недоступна
        List<TripleClass> excursionsAvailable = new ArrayList<>();

        //заранее создадим список для хранения всех экскурсий
        List<Excursions> excursionsList = new ArrayList<>();

        for (Integer excursionCode : allTourExcursionCodes)
        {
            List<LocalDate[]> excursionDates = excursionsDatesRepository.findAllExcursionDates(excursionCode);
            //тут проверяем совпадают ли даты поездки и экскурсии
            boolean b = false;
            for (LocalDate[] oneDate : excursionDates)
            {
                if(!(startClientDate.isAfter(oneDate[1]) || endClientDate.isBefore(oneDate[0])))
                    b = true;
            }
            excursionsAvailable.add(new TripleClass(excursionDates, excursionCode, b));

            //получаем данные об экскурсии
            Excursions excursion = excursionsRepository.findById(excursionCode).orElse(null);
            if (excursion != null)
                excursionsList.add(excursion);

        }

        //сохраняем в модель доступность всех экскурсий заказа <даты, код, доступность>
        model.addAttribute("excursionsAvailable", excursionsAvailable);
        //сохраняем в модель все экскурсии тура
        model.addAttribute("excursionsList", excursionsList);


        //заранее создадим объект сессии для хранения кодов туров и их числа билетов
        if (session.getAttribute("numberOfExcursionTickets") == null)
        {
            Map<Integer, Integer> numberOfExcursionTickets = new HashMap<>();
            for (Integer excursionCode : allTourExcursionCodes)
            {
                numberOfExcursionTickets.put(excursionCode, 0);
            }
            session.setAttribute("numberOfExcursionTickets", numberOfExcursionTickets);
        }
        model.addAttribute("numberOfExcursionTickets", session.getAttribute("numberOfExcursionTickets"));

        return "excursionsPage";
    }

    @GetMapping("/selectExcursion/{excursionCode}")
    public String showExcursionInformation(@PathVariable("excursionCode") Integer excursionCode, HttpSession session)
    {
        session.setAttribute("excursionThatShowInformation", excursionCode);
        return "redirect:/excursionInformationPage";
    }

    @GetMapping("/toTransport")
    public String toTransport(HttpSession session)
    {
        if (session.getAttribute("transportPrice") != null)
            session.removeAttribute("transportPrice");
        if (session.getAttribute("transportTypeCode") != null)
            session.removeAttribute("transportTypeCode");

        return "redirect:/transportPage";
    }

    @GetMapping("/toResultPage")
    public String end(HttpSession session)
    {
        Clients thisClient = (Clients) session.getAttribute("client");
        //просматриваем клиента и если он имеет поля серии и номера паспорта, то отправляем на страницу заполнения БД
        //а если не имеет, то переадресовываем на страницу заполнения серии и номера паспорта
        if (thisClient.getPassportSeries() == null || thisClient.getPassportNumber() == null)
            return "redirect:/addMoreData";
        else
            return "redirect:/rezultsPage";
    }

    @PostMapping("/saveTicketsValue")
    public String saveTicketsValue(@RequestParam("blockKey") Integer blockKey, @RequestParam("value") Integer value, HttpSession session) {
        Map<Integer, Integer> localMap = (Map) session.getAttribute("numberOfExcursionTickets");
        localMap.put(blockKey, value);
        session.setAttribute("numberOfExcursionTickets", localMap);

        return "redirect:/excursionsPage"; // Редирект обратно на страницу с экскурсиями
    }

}
