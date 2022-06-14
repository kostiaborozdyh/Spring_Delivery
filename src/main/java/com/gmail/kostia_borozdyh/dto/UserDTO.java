package com.gmail.kostia_borozdyh.dto;

import com.gmail.kostia_borozdyh.entity.Role;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
    private Integer id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}")
    private String login;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")
    private String password;

    private String secondPassword;

    @NotNull
    @Pattern(regexp = "^[a-zA-ZА-Яа-яЇїіІ]{4,20}")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-ZА-Яа-яЇїіІ]{4,20}")
    private String lastName;

    @Pattern(regexp = "\\+380\\d{9}")
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^([a-z\\d_-]+\\.)*[a-z\\d_-]+@[a-z\\d_-]+(\\.[a-z\\d_-]+)*\\.[a-z]{2,6}$")
    private String email;

    private String notify;
    private String ban;
    private Integer money;
    private Role role;
}
