package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.entity.Role;
import com.gmail.kostia_borozdyh.repository.RoleRepository;
import com.gmail.kostia_borozdyh.service.impl.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void findRoleByIdTest() {
        Role role = new Role();
        role.setId(20);
        Mockito.doReturn(role)
                .when(roleRepository)
                .getRoleById(20);

        Role newRole = roleService.findRoleById(20);

        Mockito.verify(roleRepository, Mockito.times(1)).getRoleById(20);

        assertEquals(role, newRole);
    }
}
