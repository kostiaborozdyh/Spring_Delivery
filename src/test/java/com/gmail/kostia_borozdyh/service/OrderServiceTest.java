package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.*;
import com.gmail.kostia_borozdyh.repository.LocationStatusRepository;
import com.gmail.kostia_borozdyh.repository.OrderRepository;
import com.gmail.kostia_borozdyh.repository.PaymentStatusRepository;
import com.gmail.kostia_borozdyh.service.impl.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private LocationStatusRepository locationStatusRepository;
    @MockBean
    private PaymentStatusRepository paymentStatusRepository;

    @Test
    public void saveOrderTest() {
        LocationStatus locationStatus = new LocationStatus();
        PaymentStatus paymentStatus = new PaymentStatus();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDistance(240);

        Mockito.doReturn(locationStatus)
                .when(locationStatusRepository)
                .getLocationStatusById(1);
        Mockito.doReturn(paymentStatus)
                .when(paymentStatusRepository)
                .getPaymentStatusById(1);

        Order order = orderService.saveOrder(orderDTO);

        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        Mockito.verify(paymentStatusRepository, Mockito.times(1)).getPaymentStatusById(1);
        Mockito.verify(locationStatusRepository, Mockito.times(1)).getLocationStatusById(1);

        assertEquals(Date.valueOf(LocalDate.now()), order.getDateCreate());
        assertEquals(Date.valueOf(LocalDate.now().plusDays(1)), order.getDateOfArrival());
        assertEquals(locationStatus, order.getLocationStatus());
        assertEquals(paymentStatus, order.getPaymentStatus());
    }

    @Test
    public void getOrdersByUserTest() {
        Role userRole = new Role();
        userRole.setRole("USER");
        User user = new User();
        user.setRole(userRole);
        List<Order> orderList = new ArrayList<>();
        Mockito.doReturn(orderList)
                .when(orderRepository)
                .getAllByUser(user);

        List<Order> newOrderList = orderService.getOrdersByUser(user);

        Mockito.verify(orderRepository, Mockito.times(1)).getAllByUser(user);
        Mockito.verify(orderRepository, Mockito.times(0)).findAll();

        assertEquals(orderList, newOrderList);
    }

    @Test
    public void getOrdersByUserForManagerTest() {
        Role userRole = new Role();
        userRole.setRole("MANAGER");
        User user = new User();
        user.setRole(userRole);
        List<Order> orderList = new ArrayList<>();
        Mockito.doReturn(orderList)
                .when(orderRepository)
                .findAll();

        List<Order> newOrderList = orderService.getOrdersByUser(user);

        Mockito.verify(orderRepository, Mockito.times(0)).getAllByUser(user);
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();

        assertEquals(orderList, newOrderList);
    }

    @Test
    public void getOrderByIdTest() {
        Order order = new Order();
        order.setId(102);
        Mockito.doReturn(order)
                .when(orderRepository)
                .getOrderById(102);

        Order newOrder = orderService.getOrderById(102);

        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(102);

        assertEquals(order, newOrder);
    }

    @Test
    public void updateOrderPaymentStatusByIdTest() {
        Order order = new Order();
        order.setId(102);


        PaymentStatus newPaymentStatus = new PaymentStatus();


        Mockito.doReturn(order)
                .when(orderRepository)
                .getOrderById(102);
        Mockito.doReturn(newPaymentStatus)
                .when(paymentStatusRepository)
                .getPaymentStatusById(3);


        orderService.updateOrderPaymentStatusById(102);

        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(102);
        Mockito.verify(paymentStatusRepository, Mockito.times(1)).getPaymentStatusById(3);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);

        assertEquals(order.getPaymentStatus(), newPaymentStatus);
    }

    @Test
    public void confirmOrderByIdTest() {
        User user = new User();
        user.setNotify("no");
        Order order = new Order();
        order.setUser(user);
        order.setId(102);
        order.setDateCreate(java.util.Date.from(Instant.now()));
        order.setDateOfArrival(java.util.Date.from(Instant.now().plusSeconds(172800)));

        PaymentStatus newPaymentStatus = new PaymentStatus();

        LocationStatus newLocationStatus = new LocationStatus();


        Mockito.doReturn(order)
                .when(orderRepository)
                .getOrderById(102);
        Mockito.doReturn(newPaymentStatus)
                .when(paymentStatusRepository)
                .getPaymentStatusById(2);
        Mockito.doReturn(newLocationStatus)
                .when(locationStatusRepository)
                .getLocationStatusById(2);


        Order newOrder = orderService.confirmOrderById(102);

        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(102);
        Mockito.verify(paymentStatusRepository, Mockito.times(1)).getPaymentStatusById(2);
        Mockito.verify(locationStatusRepository, Mockito.times(1)).getLocationStatusById(2);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);

        assertEquals(order, newOrder);
        assertEquals(Date.valueOf(LocalDate.now()), order.getDateOfSending());
        assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), order.getDateOfArrival());
        assertEquals(newOrder.getLocationStatus(), newLocationStatus);
        assertEquals(newOrder.getPaymentStatus(), newPaymentStatus);
    }

    @Test
    public void putOnRecordTest() {
        User user = new User();
        user.setNotify("no");
        Order order = new Order();
        order.setUser(user);
        order.setId(102);

        LocationStatus newLocationStatus = new LocationStatus();


        Mockito.doReturn(order)
                .when(orderRepository)
                .getOrderById(102);
        Mockito.doReturn(newLocationStatus)
                .when(locationStatusRepository)
                .getLocationStatusById(3);


        Order newOrder = orderService.putOnRecord(102);

        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(102);
        Mockito.verify(locationStatusRepository, Mockito.times(1)).getLocationStatusById(3);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);

        assertEquals(order, newOrder);
        assertEquals(newOrder.getLocationStatus(), newLocationStatus);
    }

    @Test
    public void giveOrderTest() {
        Order order = new Order();
        order.setId(102);

        LocationStatus newLocationStatus = new LocationStatus();


        Mockito.doReturn(order)
                .when(orderRepository)
                .getOrderById(102);
        Mockito.doReturn(newLocationStatus)
                .when(locationStatusRepository)
                .getLocationStatusById(4);


        Order newOrder = orderService.giveOrder(102);

        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(102);
        Mockito.verify(locationStatusRepository, Mockito.times(1)).getLocationStatusById(4);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);

        assertEquals(order, newOrder);
        assertEquals(newOrder.getLocationStatus(), newLocationStatus);
    }
}
