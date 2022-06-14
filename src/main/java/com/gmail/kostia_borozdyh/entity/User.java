package com.gmail.kostia_borozdyh.entity;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "notify")
    private String notify;

    @Column(name = "ban")
    private String ban;

    @Column(name = "money")
    private Integer money;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public static User of(String login, String password, String firstName, String lastName, String phoneNumber, String notify,
                          String ban, Integer money, String email, Role role) {
        return User.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .notify(notify)
                .ban(ban)
                .money(money)
                .email(email)
                .role(role)
                .build();
    }

    public static User fromDTO(UserDTO userDTO) {
        return User.of(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(),
                userDTO.getNotify(), userDTO.getBan(), userDTO.getMoney(), userDTO.getEmail(), userDTO.getRole());
    }
}
