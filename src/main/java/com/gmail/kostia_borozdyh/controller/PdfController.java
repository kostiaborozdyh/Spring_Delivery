package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import com.gmail.kostia_borozdyh.util.CreatePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@Controller
@RequestMapping(value = "/pdf")
public class PdfController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public void pdfUserOrders(HttpServletResponse response, HttpSession session) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = CreatePdf.userList((List<Order>) session.getAttribute("orders"));

        responseSettings(response, byteArrayOutputStream);

        OutputStream os = response.getOutputStream();
        byteArrayOutputStream.writeTo(os);
        os.flush();
        os.close();
    }

    @GetMapping("/orderList")
    public void pdfUserOrderList(HttpServletResponse response, HttpSession session) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = CreatePdf.managerList((List<Order>) session.getAttribute("orders"));

        responseSettings(response, byteArrayOutputStream);

        OutputStream os = response.getOutputStream();
        byteArrayOutputStream.writeTo(os);
        os.flush();
        os.close();
    }

    @GetMapping("/userOrder")
    public void pdfUserOrder(@RequestParam(name = "idOrder") Integer id, HttpServletResponse response) throws IOException {
        OrderDTO order = OrderDTO.fromOrder(orderService.getOrderById(id));

        ByteArrayOutputStream byteArrayOutputStream = CreatePdf.userOrder(order);

        responseSettings(response, byteArrayOutputStream);

        OutputStream os = response.getOutputStream();
        byteArrayOutputStream.writeTo(os);
        os.flush();
        os.close();
    }

    private void responseSettings(HttpServletResponse response, ByteArrayOutputStream byteArrayOutputStream) {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/pdf");
        response.setContentLength(byteArrayOutputStream.size());
    }
}
