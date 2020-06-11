package com.exsportsmanager.exsportsmanager.services;

import com.exsportsmanager.exsportsmanager.models.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    City save(City city);

    void delete(City city);

    List<City> findAll();

    Optional<City> findByCityName(String cityName);


}
