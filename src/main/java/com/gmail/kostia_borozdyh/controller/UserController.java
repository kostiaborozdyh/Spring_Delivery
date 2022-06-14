package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.dto.PointDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.Calculate;
import com.gmail.kostia_borozdyh.util.CreateMessage;
import com.gmail.kostia_borozdyh.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/createOrder")
    public String getCreateOrderPage() {
        return "user/createOrder";
    }

    @PostMapping("/calculateOrder")
    public String calculatePrice(@ModelAttribute OrderDTO order, HttpSession session) {
        order = orderService.calculateOrderPrice(order);
        session.setAttribute("orderDTO", order);
        session.setAttribute("btn", "unblock");
        return "redirect:/user/createOrder";
    }

    @PostMapping("/createOrder")
    public String createOrder(HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        User user = (User) session.getAttribute("user");
        orderDTO.setUser(user);
        orderService.saveOrder(orderDTO);
        session.removeAttribute("orderDTO");
        session.removeAttribute("btn");
        return "redirect:/user/createOrder";
    }

    @GetMapping("/order")
    public String getOrdersPage() {
        return "redirect:/order";
    }

    @GetMapping("/sendEmailOrder")
    public String sendEmailOrder(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("idOrder"));
        User user = (User) request.getSession().getAttribute("user");
        SendEmail.send(user.getEmail(), CreateMessage.messageSendOrder(OrderDTO.fromOrder(orderService.getOrderById(id))));
        return "user/order";
    }

    @GetMapping("/pay")
    public String payOrder(HttpServletRequest request, HttpSession session) {
        Integer orderId = Integer.parseInt(request.getParameter("id"));
        Integer value = Integer.parseInt(request.getParameter("value"));
        Integer money = Integer.parseInt(request.getParameter("money"));

        User user = (User) session.getAttribute("user");
        user.setMoney(money - value);

        userService.updateUserMoneyById(money - value, user.getId());
        orderService.updateOrderPaymentStatusById(orderId);

        return "redirect:/resetOrder";
    }

    @GetMapping("/infoAboutOrder")
    public String getInfoOrderPage(HttpServletRequest request, Model model) {
        Integer orderId = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("infoOrder", OrderDTO.fromOrder(orderService.getOrderById(orderId)));
        return "/user/userOrder";
    }

    @PostMapping("/refill")
    public String refillMoney(HttpServletRequest request, HttpSession session) {
        int value = Integer.parseInt(request.getParameter("value"));
        User user = (User) session.getAttribute("user");
        int money;

        if (user.getMoney() == null) {
            money = 0;
        } else {
            money = user.getMoney();
        }
        user.setMoney(money + value);
        userService.updateUserMoneyById(money + value, user.getId());
        return "redirect:/user/refill";
    }

    @GetMapping("/refill")
    public String getRefillMoneyPage() {
        return "/user/refill";
    }

    @GetMapping("/showLocation")
    public String getLocationPage(HttpServletRequest request, Model model) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        PointDTO currentPoint = Calculate.getPointAtTheMoment(orderService.getOrderById(id));

        model.addAttribute("latitude", currentPoint.getLatitude());
        model.addAttribute("longitude", currentPoint.getLongitude());
        return "/user/location";
    }
}
