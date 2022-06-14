package com.gmail.kostia_borozdyh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ValidationDTO {

    private String login = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phoneNumber = "";
}
