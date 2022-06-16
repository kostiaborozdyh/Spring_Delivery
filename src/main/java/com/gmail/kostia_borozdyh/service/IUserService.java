package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.entity.User;

import java.util.List;

public interface IUserService {
    User findByLogin(String login);

    User findByEmail(String email);

    void saveUser(User user);

    Long countUser();

    List<User> getUserLimit(Integer offset);

    void updateUserMoneyById(Integer money, Integer id);

    void blockUser(Integer id);

    void unblockUser(Integer id);

    void deleteUser(Integer id);

    String getUserEmailForRestore(String login);

    void updateUserPassword(String email, String password);

    User saveUser(User user, UserDTO userDTO);
}
