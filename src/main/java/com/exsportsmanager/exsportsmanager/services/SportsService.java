package com.exsportsmanager.exsportsmanager.services;

import com.exsportsmanager.exsportsmanager.models.Sport;

import java.util.List;
import java.util.Optional;

public interface SportsService {

    Sport save(Sport Sport);

    //    Not used but still there if functionality would be added in the future
    void delete(Sport Sport);

    List<Sport> findAll();

    List<Sport> findBySportName(String sportName);

    List<Sport> findByCountry_CountryName(String countryName);

    Optional<Sport> findBySportNameAndCountry_CountryName(String sportName, String countryName);


}
