package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.FilterOrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import com.gmail.kostia_borozdyh.util.Calculate;
import com.gmail.kostia_borozdyh.util.ConvertToDTO;
import com.gmail.kostia_borozdyh.util.Filtration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class OrderController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String getOrdersPage(Model model, HttpSession session) {
        List<Order> orderList = (List<Order>) session.getAttribute("orders");
        User user = (User) session.getAttribute("user");

        if (orderList == null) {
            orderList = orderService.getOrdersByUser(user);
            session.setAttribute("orders", orderList);
            log.info("add orders in session orderList for user - "+user.getLogin());
        }

        model.addAttribute("cityFromSet", Calculate.cityFromSet(orderList));
        model.addAttribute("cityToSet", Calculate.cityToSet(orderList));

        if (session.getAttribute("pageNumberOrder") == null) {
            List<Integer> list = Calculate.getPaginationList(orderList);
            if (list == null) {
                session.setAttribute("shortOrders", ConvertToDTO.fromListOrder(orderList));
            } else {
                session.setAttribute("shortOrders", ConvertToDTO.fromListOrder(Calculate.getFiveElements(orderList, 1)));
            }
            session.setAttribute("listNumberOrder", list);
            session.setAttribute("pageNumberOrder", 1);
            session.setAttribute("previousPageOrder", 0);
        }
        if (session.getAttribute("filter") == null) {
            session.setAttribute("filter", new FilterOrderDTO());
        }

        if (user.getRole().getRole().equals("USER")) {
            return "user/order";
        }
        return "man/orderList";
    }

    @PostMapping("/filtrationOrder")
    public String filtrationOrder(@ModelAttribute FilterOrderDTO filterOrder, HttpSession session) {

        List<Order> orderList = orderService.getOrdersByUser((User) session.getAttribute("user"));
        orderList = Filtration.doFilter(orderList, filterOrder);

        session.setAttribute("filter", filterOrder);
        session.removeAttribute("pageNumberOrder");
        session.setAttribute("orders", orderList);

        return "redirect:/order";
    }

    @GetMapping("/resetOrder")
    public String updateOrdersFilter(HttpSession session) {
        session.removeAttribute("filter");
        session.removeAttribute("pageNumberOrder");
        session.removeAttribute("orders");
        return "redirect:/order";
    }

    @PostMapping("/resetOrder")
    public String resetOrdersFilter() {
        return "redirect:/resetOrder";
    }
}
