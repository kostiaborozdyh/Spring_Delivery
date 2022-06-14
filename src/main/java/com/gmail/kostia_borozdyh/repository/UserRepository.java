package com.gmail.kostia_borozdyh.repository;

import com.gmail.kostia_borozdyh.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);

    User findByEmail(String email);

    User getById(Integer id);

    @Query(value = "Select u.* from user u where u.role_id !=3 order by u.id asc limit ?1,  ?2 ", nativeQuery = true)
    List<User> findUsers(int offset, int limit);
}
