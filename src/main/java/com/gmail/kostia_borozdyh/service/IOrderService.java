package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.User;

import java.util.List;

public interface IOrderService {
    OrderDTO calculateOrderPrice(OrderDTO orderDTO);

    Order saveOrder(OrderDTO orderDTO);

    List<Order> getOrdersByUser(User user);

    Order getOrderById(Integer id);

    void updateOrderPaymentStatusById(Integer id);

    Order confirmOrderById(Integer id);

    List<Order> getOrdersByCity(String city);

    List<Order> getAcceptOrdersByCity(String city);

    Order putOnRecord(Integer id);

    Order giveOrder(Integer id);
}
