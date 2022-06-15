package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.repository.UserRepository;
import com.gmail.kostia_borozdyh.service.IUserService;
import com.gmail.kostia_borozdyh.util.CreateMessage;
import com.gmail.kostia_borozdyh.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService, UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder=passwordEncoder;}

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public List<User> getUserLimit(Integer lim) {
        return userRepository.findUsers(lim, 5);
    }

    @Override
    public void updateUserMoneyById(Integer money, Integer id) {
        User user = userRepository.getById(id);
        user.setMoney(money);
        userRepository.save(user);
    }

    @Override
    public void blockUser(Integer id) {
        User user = userRepository.getById(id);
        user.setBan("yes");
        userRepository.save(user);
        SendEmail.send(user.getEmail(), CreateMessage.blockUser());
    }

    @Override
    public void unblockUser(Integer id) {
        User user = userRepository.getById(id);
        user.setBan("no");
        userRepository.save(user);
        SendEmail.send(user.getEmail(), CreateMessage.unBlockUser());
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
        SendEmail.send(user.getEmail(), CreateMessage.deleteUser());
    }

    @Override
    public String getUserEmailForRestore(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            user = userRepository.findByEmail(login);
        }

        return (user == null) ? null : user.getEmail();
    }

    @Override
    public void updateUserPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        saveUser(user);
    }

    @Override
    public User saveUser(User user, UserDTO userDTO) {
        user.setPassword(userRepository.findByLogin(user.getLogin()).getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        if (!userDTO.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", login));
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));

        boolean block = user.getBan().equals("no");

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, block, roles);
    }

}
