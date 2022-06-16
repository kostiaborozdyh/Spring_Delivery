package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public String getInfoOrderPage(@RequestParam(name = "id") Integer orderId, Model model) {
        model.addAttribute("infoOrder", OrderDTO.fromOrder(orderService.getOrderById(orderId)));
        return "man/orderUser";
    }

    @GetMapping("/confirmOrder")
    public String getCreateOrderPage(@RequestParam(name = "id") Integer orderId) {
        orderService.confirmOrderById(orderId);
        return "redirect:/man/infoAboutOrder?id=" + orderId;
    }

}
