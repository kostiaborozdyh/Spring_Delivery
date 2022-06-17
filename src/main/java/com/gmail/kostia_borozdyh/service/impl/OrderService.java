package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.dto.InfoTableDTO;
import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.repository.LocationStatusRepository;
import com.gmail.kostia_borozdyh.repository.OrderRepository;
import com.gmail.kostia_borozdyh.repository.PaymentStatusRepository;
import com.gmail.kostia_borozdyh.service.IOrderService;
import com.gmail.kostia_borozdyh.util.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class OrderService implements IOrderService {
    private OrderRepository orderRepository;
    private LocationStatusRepository locationStatusRepository;
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    public void setLocationStatusRepository(LocationStatusRepository locationStatusRepository) {
        this.locationStatusRepository = locationStatusRepository;
    }

    @Autowired
    public void setPaymentStatusRepository(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO calculateOrderPrice(OrderDTO orderDTO) {
        List<InfoTableDTO> infoTable;
        try {
            log.info("calculating distance between cityFrom - "+orderDTO.getCityFrom()+" and cityTo - "+orderDTO.getCityTo());
            infoTable = GoogleMaps.getDistance(orderDTO.getCityFrom(), orderDTO.getCityTo());
        } catch (ParseException e) {
            log.error("problem with parsing data that we take from GoogleAPI");
            log.error("Exception - "+e);
            throw new RuntimeException(e);
        }
        int volume = Calculate.volume(orderDTO.getHeight(), orderDTO.getLength(), orderDTO.getWidth());
        int price = Calculate.deliveryPrice(infoTable.get(0).getDistance(), volume, orderDTO.getWeight());
        orderDTO.setCityFrom(infoTable.get(0).getCityFrom());
        orderDTO.setCityTo(infoTable.get(0).getCityTo());
        orderDTO.setDistance(infoTable.get(0).getDistance());
        orderDTO.setVolume(volume);
        orderDTO.setPrice(price);
        return orderDTO;
    }

    @Override
    public Order saveOrder(OrderDTO orderDTO) {
        Order order = Order.fromDTO(orderDTO);
        order.setDateCreate(Date.valueOf(LocalDate.now()));
        order.setDateOfArrival(Date.valueOf(Calculate.arrivalTime(orderDTO.getDistance())));
        order.setLocationStatus(locationStatusRepository.getLocationStatusById(1));
        order.setPaymentStatus(paymentStatusRepository.getPaymentStatusById(1));
        orderRepository.save(order);
        log.info("save order to dataBase");
        return order;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        log.info("get orders from dataBase by userRole - "+user.getRole().getRole());
        if (user.getRole().getRole().equals("USER")) {
            return orderRepository.getAllByUser(user);
        }
        return orderRepository.findAll();

    }

    @Override
    public Order getOrderById(Integer id) {
        log.info("get orders from dataBase by orderId - "+id);
        return orderRepository.getOrderById(id);
    }

    @Override
    public void updateOrderPaymentStatusById(Integer id) {
        Order order = orderRepository.getOrderById(id);
        order.setPaymentStatus(paymentStatusRepository.getPaymentStatusById(3));
        orderRepository.save(order);
        log.info("change paymentStatus order with id - "+id+" on payment status with id 3");
    }

    @Override
    public Order confirmOrderById(Integer id) {
        Order order = orderRepository.getOrderById(id);
        order.setPaymentStatus(paymentStatusRepository.getPaymentStatusById(2));
        order.setLocationStatus(locationStatusRepository.getLocationStatusById(2));
        order.setDateOfSending(Date.valueOf(LocalDate.now()));
        order.setDateOfArrival(Date.valueOf(Calculate.newArrivalTime(order.getDateCreate(), order.getDateOfArrival())));
        orderRepository.save(order);
        if (order.getUser().getNotify().equals("yes")) {
            SendEmail.send(order.getUser().getEmail(), CreateMessage.messageChangePaymentStatus(order.getPrice()));
        }
        log.info("confirm order by id - "+id);
        return order;
    }

    @Override
    public List<Order> getOrdersByCity(String city) {
        log.info("get orders from dateBase with field cityTo - "+city+" and location status with id 3");
        return orderRepository.findAllByCityToAndLocationStatus(JsonParser.cutCityName(city), locationStatusRepository.getLocationStatusById(3));
    }

    @Override
    public List<Order> getAcceptOrdersByCity(String city) {
        log.info("get orders from dateBase with field cityTo - "+city+" and location status with id 2 and date of arrival under "+LocalDate.now());
        return orderRepository.findAllByCityToAndLocationStatusAndDateOfArrivalIsBefore(JsonParser.cutCityName(city), locationStatusRepository.getLocationStatusById(2), Date.valueOf(LocalDate.now().plusDays(1)));
    }

    @Override
    public Order putOnRecord(Integer id) {
        Order order = orderRepository.getOrderById(id);
        order.setLocationStatus(locationStatusRepository.getLocationStatusById(3));
        orderRepository.save(order);
        User user = order.getUser();
        if (user.getNotify().equals("yes")) {
            SendEmail.send(user.getEmail(), CreateMessage.putOnRecord(id));
        }
        log.info("put on record(change location status) order by id "+id);
        return order;
    }

    @Override
    public Order giveOrder(Integer id) {
        Order order = orderRepository.getOrderById(id);
        order.setLocationStatus(locationStatusRepository.getLocationStatusById(4));
        orderRepository.save(order);
        log.info("give(change location status) order by id "+id);
        return order;
    }
}
