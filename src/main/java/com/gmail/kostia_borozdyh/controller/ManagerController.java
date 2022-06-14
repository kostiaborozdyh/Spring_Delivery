package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/man")
public class ManagerController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orderList")
    public String getOrderListPage() {
        return "redirect:/order";
    }

    @GetMapping("/infoAboutOrder")
    public String getInfoOrderPage(HttpServletRequest request, Model model) {
        Integer orderId = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("infoOrder", OrderDTO.fromOrder(orderService.getOrderById(orderId)));
        return "man/orderUser";
    }

    @GetMapping("/confirmOrder")
    public String getCreateOrderPage(HttpServletRequest request) {
        Integer orderId = Integer.parseInt(request.getParameter("id"));
        orderService.confirmOrderById(orderId);
        return "redirect:/man/infoAboutOrder?id=" + orderId;
    }

}
