package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.service.impl.InfoTableService;
import com.gmail.kostia_borozdyh.util.Filtration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class InfoTableController {
    private InfoTableService infoTableService;

    @Autowired
    public void setInfoTableService(InfoTableService infoTableService) {
        this.infoTableService = infoTableService;
    }

    @GetMapping("/info")
    public String getInfoPage() {
        return "info";
    }

    @PostMapping("/showInfo")
    public String showInfoTable(HttpServletRequest request, HttpSession session) {
        String cityFrom = getRequestCityString(request, "cityFrom");
        String cityTo = getRequestCityString(request, "cityTo");

        List<InfoTableDTO> infoTableList = infoTableService.getInfoTable(cityFrom, cityTo);
        List<Integer> pageNumberList = infoTableService.getPageNumberList(infoTableList);
        List<InfoTableDTO> shortInfoTableList = infoTableService.getShortInfoTable(infoTableList, pageNumberList);

        session.setAttribute("infoTable", infoTableList);
        session.setAttribute("shortInfoTable", shortInfoTableList);
        session.setAttribute("list", pageNumberList);
        session.setAttribute("pageNumber", 1);
        session.setAttribute("previousPage", 0);
        session.removeAttribute("table");

        return "redirect:/info";
    }

    @PostMapping("/sortTable")
    public String sortInfoTable(HttpServletRequest request, HttpSession session) {
        String sort = request.getParameter("sort");

        List<InfoTableDTO> infoTableList = (List<InfoTableDTO>) session.getAttribute("infoTable");
        List<Integer> pageNumberList = (List<Integer>) session.getAttribute("list");

        infoTableList = Filtration.sortingTable(sort, infoTableList);
        List<InfoTableDTO> shortInfoTableList = infoTableService.getShortInfoTable(infoTableList, pageNumberList);

        session.setAttribute("infoTable", infoTableList);
        session.setAttribute("shortInfoTable", shortInfoTableList);
        session.setAttribute("pageNumber", 1);
        session.setAttribute("previousPage", 0);
        session.setAttribute("sort", sort);
        return "redirect:/info";
    }

    @GetMapping("/showMap")
    public String showMap(HttpServletRequest request, HttpSession session) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<InfoTableDTO> infoTable = (List<InfoTableDTO>) session.getAttribute("infoTable");
        session.setAttribute("table", infoTableService.getInfoTableById(infoTable, id));
        return "redirect:/info";
    }

    private String getRequestCityString(HttpServletRequest request, String city) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String string = request.getParameter(city + (i + 1));
            if (string != null) {
                stringBuilder.append("|").append(string);
            }
        }
        stringBuilder.deleteCharAt(0);
        return stringBuilder.toString();
    }
}
