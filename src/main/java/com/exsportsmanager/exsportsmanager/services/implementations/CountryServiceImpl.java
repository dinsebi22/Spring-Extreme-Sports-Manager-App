package com.exsportsmanager.exsportsmanager.services.implementations;

import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import com.exsportsmanager.exsportsmanager.repositories.CountryRepository;
import com.exsportsmanager.exsportsmanager.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void delete(Country country) {
        this.countryRepository.delete(country);
    }

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Optional<Country> findByCountryName(String countryName) {
        return this.countryRepository.findByCountryName(countryName);
    }

    @Override
    public List<Country> findByRegion(Regions region) {
        return countryRepository.findByRegion(region);
    }

    @Override
    public List<Country> findBySports_SportName(String sportName) {
        return this.countryRepository.findBySports_SportName(sportName);
    }

    @Override
    public Optional<Country> findByCities_CityName(String cityName) {
        return this.countryRepository.findByCities_CityName(cityName);
    }


}
