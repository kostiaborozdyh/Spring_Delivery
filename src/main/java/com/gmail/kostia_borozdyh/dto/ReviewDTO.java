package com.gmail.kostia_borozdyh.dto;

import com.gmail.kostia_borozdyh.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReviewDTO {

    private String firstName;
    private String response;
    private String date;

}
