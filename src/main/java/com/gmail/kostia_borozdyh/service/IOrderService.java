package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.User;

import java.util.List;

public interface IOrderService {
    OrderDTO calculateOrderPrice(OrderDTO orderDTO);

    void saveOrder(OrderDTO orderDTO);

    List<Order> getOrdersByUser(User user);

    Order getOrderById(Integer id);

    void updateOrderPaymentStatusById(Integer id);

    void confirmOrderById(Integer id);

    List<Order> getOrdersByCity(String city);

    List<Order> getAcceptOrdersByCity(String city);

    void putOnRecord(Integer id);

    void giveOrder(Integer id);
}
