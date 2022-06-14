package com.gmail.kostia_borozdyh.repository;


import com.gmail.kostia_borozdyh.entity.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationStatusRepository extends JpaRepository<LocationStatus, Integer> {
    LocationStatus getLocationStatusById(Integer id);
}
