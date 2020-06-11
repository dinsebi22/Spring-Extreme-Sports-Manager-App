package com.exsportsmanager.exsportsmanager.services;


import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Country save(Country Country);

    void delete(Country Country);

    List<Country> findAll();

    Optional<Country> findByCountryName(String country_name);

    List<Country> findByRegion(Regions region);

    List<Country> findBySports_SportName(String sportName);

    Optional<Country> findByCities_CityName(String cityName);

}
