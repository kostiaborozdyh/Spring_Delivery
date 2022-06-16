package com.gmail.kostia_borozdyh.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ManagerControllerTest {
    @Autowired
    private MainController controller;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithUserDetails("manager")
    public void authorizationManagerTest() throws Exception {
        this.mockMvc.perform(get("/")).
                andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/order"));
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(get("/man/orderList")).
                andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}