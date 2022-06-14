package com.gmail.kostia_borozdyh.repository;

import com.gmail.kostia_borozdyh.entity.LocationStatus;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderById(Integer id);

    List<Order> getAllByUser(User user);

    List<Order> findAll();

    List<Order> findAllByCityToAndLocationStatus(String city, LocationStatus locationStatus);

    List<Order> findAllByCityToAndLocationStatusAndDateOfArrivalIsBefore(String city, LocationStatus locationStatus, Date date);
}
