package com.exsportsmanager.exsportsmanager.services;

import com.exsportsmanager.exsportsmanager.models.Date;

import java.util.List;
import java.util.Optional;

public interface DateService {

    Date save(Date date);

    void delete(Date date);

    List<Date> findAllDates();

    List<Date> findBySport_SportName(String sportName);

    List<Date> findBySport_Country_CountryName(String countryName);

    Optional<Date> findBySport_Country_CountryNameAndSport_SportName(String countryName, String sportName);


}
