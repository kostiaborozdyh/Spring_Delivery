package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.util.Calculate;
import com.gmail.kostia_borozdyh.util.ConvertToDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChangePageController {

    @GetMapping("/changePage")
    public String changePage(HttpServletRequest request, HttpSession session) {
        int id = Integer.parseInt(request.getParameter("id"));
        int fun = Integer.parseInt(request.getParameter("fun"));
        String referer = request.getHeader("Referer");
        switch (fun) {
            case 1: {
                List<InfoTableDTO> infoTable = (List<InfoTableDTO>) session.getAttribute("infoTable");
                List<Integer> list = (List<Integer>) session.getAttribute("list");
                id = Calculate.pageId(id, list);
                session.setAttribute("shortInfoTable", Calculate.getFiveElements(infoTable, id));
                session.setAttribute("pageNumber", id);
                session.setAttribute("previousPage", id - 1);
                return "redirect:" + referer;
            }
            case 2: {
                List<Order> orderList = (List<Order>) session.getAttribute("orders");
                List<Integer> list = (List<Integer>) session.getAttribute("listNumberOrder");
                id = Calculate.pageId(id, list);
                session.setAttribute("shortOrders", ConvertToDTO.fromListOrder(Calculate.getFiveElements(orderList, id)));
                session.setAttribute("pageNumberOrder", id);
                session.setAttribute("previousPageOrder", id - 1);
                return "redirect:" + referer;
            }
            case 3: {
                List<Integer> list = (List<Integer>) session.getAttribute("listNumberUser");
                id = Calculate.pageId(id, list);
                session.setAttribute("pageNumberUser", id);
                session.setAttribute("previousPageUser", id - 1);
                return "redirect:" + referer;
            }

        }
        return "error";
    }
}