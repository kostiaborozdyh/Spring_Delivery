package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CalculatePriceController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/calculate")
    public String getCalculatePricePage(Model model, HttpSession session) {
        model.addAttribute("calculateTable", session.getAttribute("calculateTable"));
        session.removeAttribute("calculateTable");
        return "calculate";
    }

    @PostMapping("/calculatePrice")
    public String calculatePrice(@ModelAttribute OrderDTO order, HttpSession session) {
        order = orderService.calculateOrderPrice(order);
        session.setAttribute("calculateTable", order);
        return "redirect:/calculate";
    }
}
