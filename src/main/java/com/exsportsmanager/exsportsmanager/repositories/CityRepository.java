package com.exsportsmanager.exsportsmanager.repositories;

import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    City save(City city);

    void delete(City city);

    List<City> findAll();

    Optional<City> findByCityName(String cityName);

}
