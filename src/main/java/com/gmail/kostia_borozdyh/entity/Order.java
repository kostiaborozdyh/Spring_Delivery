package com.gmail.kostia_borozdyh.entity;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Builder(toBuilder = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "price")
    private Integer price;

    @Column(name = "address")
    private String address;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_of_sending")
    private Date dateOfSending;

    @Column(name = "date_of_arrival")
    private Date dateOfArrival;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "city_from")
    private String cityFrom;

    @Column(name = "city_to")
    private String cityTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_status_id")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_status_id")
    private LocationStatus locationStatus;


    public static Order of(String description, Integer weight, Integer volume, Integer price, String address, User user,
                           String cityFrom, String cityTo) {
        return Order.builder()
                .description(description)
                .weight(weight)
                .volume(volume)
                .price(price)
                .address(address)
                .user(user)
                .cityFrom(cityFrom)
                .cityTo(cityTo)
                .build();
    }

    public static Order fromDTO(OrderDTO orderDTO) {
        return Order.of(orderDTO.getDescription(), orderDTO.getWeight(), orderDTO.getVolume(), orderDTO.getPrice(),
                orderDTO.getAddress(), orderDTO.getUser(), orderDTO.getCityFrom(), orderDTO.getCityTo());
    }

}
