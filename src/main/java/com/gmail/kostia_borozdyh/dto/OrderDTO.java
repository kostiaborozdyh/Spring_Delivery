package com.gmail.kostia_borozdyh.dto;

import com.gmail.kostia_borozdyh.entity.LocationStatus;
import com.gmail.kostia_borozdyh.entity.Order;
import com.gmail.kostia_borozdyh.entity.PaymentStatus;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.util.CutDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderDTO {

    private Integer id;
    private String description;
    private Integer weight;
    private Integer height;
    private Integer length;
    private Integer width;
    private Integer volume;
    private Integer price;
    private String address;
    private String dateCreate;
    private String dateOfSending;
    private String dateOfArrival;
    private Integer distance;
    private User user;
    private String cityFrom;
    private String cityTo;
    private PaymentStatus paymentStatus;
    private LocationStatus locationStatus;

    public static OrderDTO of(Integer id, String description, Integer weight, Integer volume, Integer price, String address,
                              String dateCreate, String dateOfSending, String dateOfArrival, User user, String cityFrom, String cityTo, PaymentStatus paymentStatus,
                              LocationStatus locationStatus) {
        return OrderDTO.builder()
                .id(id)
                .description(description)
                .weight(weight)
                .volume(volume)
                .price(price)
                .address(address)
                .dateCreate(dateCreate)
                .dateOfSending(dateOfSending)
                .dateOfArrival(dateOfArrival)
                .user(user)
                .cityFrom(cityFrom)
                .cityTo(cityTo)
                .paymentStatus(paymentStatus)
                .locationStatus(locationStatus)
                .build();
    }

    public static OrderDTO fromOrder(Order order) {
        return OrderDTO.of(order.getId(), order.getDescription(), order.getWeight(), order.getVolume(), order.getPrice(), order.getAddress(),
                CutDate.cut(order.getDateCreate()), CutDate.cut(order.getDateOfSending()), CutDate.cut(order.getDateOfArrival()), order.getUser(),
                order.getCityFrom(), order.getCityTo(), order.getPaymentStatus(), order.getLocationStatus());
    }
}
