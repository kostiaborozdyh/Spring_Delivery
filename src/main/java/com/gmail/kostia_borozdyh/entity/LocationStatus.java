package com.gmail.kostia_borozdyh.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "location_status")
public class LocationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "location")
    private String location;
}
