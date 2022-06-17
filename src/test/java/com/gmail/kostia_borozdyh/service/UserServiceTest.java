package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.repository.UserRepository;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void findByLoginTest(){
        String login = "login";
        User user = new User();
        user.setLogin(login);

        Mockito.doReturn(user)
                .when(userRepository)
                .findByLogin(login);

        User newUser = userService.findByLogin(login);

        Mockito.verify(userRepository,Mockito.times(1)).findByLogin(login);
        assertEquals(user,newUser);
    }

    @Test
    public void findByEmailTest(){
        String email = "kostiaborozdyh@gmail.com";
        User user = new User();
        user.setEmail(email);

        Mockito.doReturn(user)
                .when(userRepository)
                .findByEmail(email);

        User newUser = userService.findByEmail(email);

        Mockito.verify(userRepository,Mockito.times(1)).findByEmail(email);
        assertEquals(user,newUser);
    }


    @Test
    public void updateUserMoneyByIdTest() {
        User user = new User();
        user.setId(100);
        user.setMoney(30);

        Mockito.doReturn(user)
                .when(userRepository)
                .getById(100);

        userService.updateUserMoneyById(50,100);

        assertEquals(50, (int) user.getMoney());

        Mockito.verify(userRepository,Mockito.times(1)).save(user);
    }

    @Test
    public void blockUserTest(){
        User user = new User();
        user.setBan("no");
        user.setId(101);

        Mockito.doReturn(user)
                .when(userRepository)
                .getById(101);

        userService.blockUser(101);

        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        assertEquals("yes",user.getBan());
    }

    @Test
    public void unblockUserTest(){
        User user = new User();
        user.setBan("yes");
        user.setId(101);

        Mockito.doReturn(user)
                .when(userRepository)
                .getById(101);

        userService.unblockUser(101);

        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        assertEquals("no",user.getBan());
    }

    @Test
    public void deleteUserTest(){
        User user = new User();
        user.setId(101);

        Mockito.doReturn(user)
                .when(userRepository)
                .getById(101);
        User newUser = userRepository.getById(101);

        userService.deleteUser(101);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
        assertEquals(user,newUser);
    }
    @Test
    public void getUserEmailForRestoreWithLoginTest(){
        String login = "login";
        String email = "email@email.com";
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        Mockito.doReturn(user)
                .when(userRepository)
                .findByLogin(login);
        String userEmail = userService.getUserEmailForRestore(login);
        Mockito.verify(userRepository,Mockito.times(1)).findByLogin(login);
        Mockito.verify(userRepository,Mockito.times(0)).findByEmail(login);
        assertEquals(user.getEmail(),userEmail);

    }
    @Test
    public void getUserEmailForRestoreWithEmailTest(){
        String email = "email@email.com";
        User user = new User();
        user.setEmail(email);
        Mockito.doReturn(null)
                .when(userRepository)
                .findByLogin(email);
        Mockito.doReturn(user)
                .when(userRepository)
                .findByEmail(email);
        String userEmail = userService.getUserEmailForRestore(email);
        Mockito.verify(userRepository,Mockito.times(1)).findByLogin(email);
        Mockito.verify(userRepository,Mockito.times(1)).findByEmail(email);
        assertEquals(user.getEmail(),userEmail);

    }

    @Test
    public void getUserEmailForRestoreWithNullTest(){
        String email = "ema";
        User user = new User();
        user.setEmail(email);
        Mockito.doReturn(null)
                .when(userRepository)
                .findByLogin(email);
        Mockito.doReturn(null)
                .when(userRepository)
                .findByEmail(email);
        String userEmail = userService.getUserEmailForRestore(email);
        Mockito.verify(userRepository,Mockito.times(1)).findByLogin(email);
        Mockito.verify(userRepository,Mockito.times(1)).findByEmail(email);
        assertNull(userEmail);

    }

    @Test
    public void updateUserPasswordTest(){
        String email = "email";
        User user = new User();
        user.setEmail(email);
        Mockito.doReturn(user)
                .when(userRepository)
                .findByEmail(email);
        Mockito.doReturn("123")
                .when(passwordEncoder)
                .encode("11");
        userService.updateUserPassword(email,"11");
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        assertEquals(user.getPassword(),"123");

    }

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setPassword("password");
        user.setLogin("login");
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setEmail("email");
        userDTO.setPhoneNumber("phoneNumber");
        userDTO.setPassword("");
        Mockito.doReturn(user)
                .when(userRepository)
                .findByLogin("login");
        userService.saveUser(user,userDTO);
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        assertEquals(user.getPassword(),"password");
        assertEquals(user.getFirstName(),"firstName");
        assertEquals(user.getLastName(),"lastName");
        assertEquals(user.getEmail(),"email");
        assertEquals(user.getPhoneNumber(),"phoneNumber");
    }

    @Test
    public void saveUserWithPasswordTest(){
        User user = new User();
        user.setPassword("password12345");
        user.setLogin("login");
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        Mockito.doReturn(user)
                .when(userRepository)
                .findByLogin("login");
        Mockito.doReturn("password123")
                .when(passwordEncoder)
                .encode("password");
        userService.saveUser(user,userDTO);
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        assertEquals(user.getPassword(),"password123");
    }

}
