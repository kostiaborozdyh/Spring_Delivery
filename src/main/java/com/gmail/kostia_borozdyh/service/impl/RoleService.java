package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.entity.Role;
import com.gmail.kostia_borozdyh.repository.RoleRepository;
import com.gmail.kostia_borozdyh.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.getRoleById(id);
    }
}
