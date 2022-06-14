package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.service.impl.OrderService;
import com.gmail.kostia_borozdyh.util.GenerateCode;
import com.gmail.kostia_borozdyh.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/ordersTable")
    public String getOrdersTablePage(Model model, HttpSession session) {
        String city = (String) session.getAttribute("city");
        if (city != null) {
            model.addAttribute("orders", orderService.getOrdersByCity(city));
        }
        return "employee/ordersTable";
    }

    @PostMapping("/chooseCity")
    public String chooseCity(HttpSession session, HttpServletRequest request) {
        String city = request.getParameter("city");
        city = JsonParser.cutCityNameForEmployee(city);
        session.setAttribute("city", city);
        return "redirect:/employee/ordersTable";
    }

    @PostMapping("/changeCity")
    public String changeCity(HttpSession session) {
        session.removeAttribute("city");
        return "redirect:/employee/ordersTable";
    }

    @PostMapping("/update")
    public String update() {
        return "redirect:/employee/ordersTable";
    }

    @GetMapping("/acceptOrder")
    public String getAcceptOrderPage(Model model, HttpSession session) {
        String city = (String) session.getAttribute("city");
        if (city == null) {
            return "redirect:/employee/ordersTable";
        } else {
            model.addAttribute("orderList", orderService.getAcceptOrdersByCity(city));
        }
        return "employee/acceptOrder";
    }

    @PostMapping("/putOnRecord")
    public String putOnRecord(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        orderService.putOnRecord(id);
        return "redirect:/employee/acceptOrder";
    }

    @PostMapping("/giveOrder")
    public String giveOrder(HttpServletRequest request, HttpSession session) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        String code = GenerateCode.sendCode(orderService.getOrderById(id).getUser().getEmail());
        session.setAttribute("code", code);
        session.setAttribute("idOrder", id);
        return "redirect:/employee/enterCode";
    }

    @GetMapping("/enterCode")
    public String getEnterCodePage(HttpSession session, Model model) {
        if (session.getAttribute("invalidCode") != null) {
            model.addAttribute("invalidCode", "invalidCode");
            session.removeAttribute("invalidCode");
        }
        return "employee/enterCode";
    }

    @PostMapping("/give")
    public String giveOrderToUser(HttpServletRequest request, HttpSession session) {
        String code = request.getParameter("code");
        String trueCode = (String) session.getAttribute("code");
        if (code.equals(trueCode)) {
            Integer id = (Integer) session.getAttribute("idOrder");
            orderService.giveOrder(id);
            session.removeAttribute("idOrder");
            session.removeAttribute("code");
            return "redirect:/employee/ordersTable";
        } else {
            session.setAttribute("invalidCode", "invalidCode");
            return "redirect:/employee/enterCode";
        }

    }
}
