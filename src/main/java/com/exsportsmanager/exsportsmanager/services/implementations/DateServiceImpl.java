package com.exsportsmanager.exsportsmanager.services.implementations;

import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.repositories.DateRepository;
import com.exsportsmanager.exsportsmanager.services.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DateServiceImpl implements DateService {

    private DateRepository dateRepository;

    @Autowired
    public DateServiceImpl(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    @Override
    public Date save(Date date) {
        return this.dateRepository.save(date);
    }

    @Override
    public void delete(Date date) {
        this.dateRepository.delete(date);
    }

    @Override
    public List<Date> findAllDates() {
        return this.dateRepository.findAll();
    }

    @Override
    public List<Date> findBySport_SportName(String sportName) {
        return this.dateRepository.findBySport_SportName(sportName);
    }

    @Override
    public List<Date> findBySport_Country_CountryName(String countryName) {
        return this.dateRepository.findBySport_Country_CountryName(countryName);
    }

    @Override
    public Optional<Date> findBySport_Country_CountryNameAndSport_SportName(String countryName, String sportName) {
        return this.dateRepository.findBySport_Country_CountryNameAndSport_SportName(countryName, sportName);
    }
}
