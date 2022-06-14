package com.gmail.kostia_borozdyh.repository;

import com.gmail.kostia_borozdyh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role getRoleById(Integer id);
}
