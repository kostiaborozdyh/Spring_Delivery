package com.gmail.kostia_borozdyh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FilterOrderDTO {
    private String minPrice;
    private String maxPrice;
    private String[] paymentStatus = {"", "", ""};
    private String[] locationStatus = {"", "", "", ""};
    private String minDateCreate;
    private String maxDateCreate;
    private String minDateOfArrival;
    private String maxDateOfArrival;
    private String[] cityFrom;
    private String[] cityTo;
    private String sort;
}
