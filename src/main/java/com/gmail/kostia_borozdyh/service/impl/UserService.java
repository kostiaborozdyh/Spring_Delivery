package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.repository.UserRepository;
import com.gmail.kostia_borozdyh.service.IUserService;
import com.gmail.kostia_borozdyh.util.CreateMessage;
import com.gmail.kostia_borozdyh.util.SendEmail;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("get from dataBase user with login - "+login);
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        log.info("get from dataBase user with email - "+email);
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        log.info("save user to dataBase with login - "+user.getLogin());
        userRepository.save(user);
    }

    @Override
    public Long countUser() {
        log.info("counting user in dataBase");
        return userRepository.count();
    }

    @Override
    public List<User> getUserLimit(Integer offset) {
        log.info("limit userList from dataBase with offset - "+offset);
        return userRepository.findUsers(offset, 5);
    }

    @Override
    public void updateUserMoneyById(Integer money, Integer id) {
        User user = userRepository.getById(id);
        user.setMoney(money);
        userRepository.save(user);
        log.info("update user money in dataBase by userId - "+id+".Now he has - "+user.getMoney()+"$");
    }

    @Override
    public void blockUser(Integer id) {
        User user = userRepository.getById(id);
        user.setBan("yes");
        userRepository.save(user);
        SendEmail.send(user.getEmail(), CreateMessage.blockUser());
        log.info("Blocking user by id - "+id+". Now state his ban: "+user.getBan());
    }

    @Override
    public void unblockUser(Integer id) {
        User user = userRepository.getById(id);
        user.setBan("no");
        userRepository.save(user);
        SendEmail.send(user.getEmail(), CreateMessage.unBlockUser());
        log.info("Unblocking user by id - "+id+". Now state his ban: "+user.getBan());
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
        SendEmail.send(user.getEmail(), CreateMessage.deleteUser());
        log.info("Delete user by id - "+id);
    }

    @Override
    public String getUserEmailForRestore(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            user = userRepository.findByEmail(login);
        }

        log.info("get user from dataBase by login or email"+login);

        return (user == null) ? null : user.getEmail();
    }

    @Override
    public void updateUserPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("update user password in dataBase by user email"+email);
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
        log.info("register user and save in dateBase with login - "+user.getLogin());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            log.info("cannot find user in dataBase by user login - "+login);
            throw new UsernameNotFoundException(String.format("User '%s' not found", login));
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));

        boolean block = user.getBan().equals("no");
        log.info("get user from dataBase by login - "+login+", and he hasn't ban: "+block);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, block, roles);
    }

}
