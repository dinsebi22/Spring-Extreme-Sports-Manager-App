package com.exsportsmanager.exsportsmanager.services.implementations;

import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.repositories.CityRepository;
import com.exsportsmanager.exsportsmanager.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;


    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City save(City city) {
        return this.cityRepository.save(city);
    }

    @Override
    public void delete(City city) {
        this.cityRepository.delete(city);
    }

    @Override
    public List<City> findAll() {
        return this.cityRepository.findAll();
    }

    @Override
    public Optional<City> findByCityName(String cityName) {
        return this.cityRepository.findByCityName(cityName);
    }
}
