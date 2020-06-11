package com.exsportsmanager.exsportsmanager.repositories;

import com.exsportsmanager.exsportsmanager.models.Date;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DateRepository extends JpaRepository<Date, Long> {


    Date save(Date date);

    void delete(Date date);

    List<Date> findAll();

    List<Date> findBySport_SportName(String sportName);

    List<Date> findBySport_Country_CountryName(String countryName);

    Optional<Date> findBySport_Country_CountryNameAndSport_SportName(String countryName,String sportName);



}
