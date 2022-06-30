package com.gmail.kostia_borozdyh;

import com.gmail.kostia_borozdyh.config.SecurityConfig;
import com.gmail.kostia_borozdyh.controller.MainController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(post("/loginUser").param("login", "user").param("password", "qazwsxedcA7")).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/"));
    }

    @Test
    public void unCorrectLoginTest() throws Exception {
        this.mockMvc.perform(post("/loginUser").param("login", "useree").param("password", "qazwsxedcA7")).
                andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

}
