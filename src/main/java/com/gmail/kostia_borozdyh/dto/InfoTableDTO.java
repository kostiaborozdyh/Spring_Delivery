package com.gmail.kostia_borozdyh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InfoTableDTO {
    private Integer id;
    private String cityFrom;
    private String cityTo;
    private Integer distance;
    private Integer price;

}
