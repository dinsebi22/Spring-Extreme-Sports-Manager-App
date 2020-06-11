package com.exsportsmanager.exsportsmanager.services.implementations;

import com.exsportsmanager.exsportsmanager.models.Sport;
import com.exsportsmanager.exsportsmanager.repositories.SportRepository;
import com.exsportsmanager.exsportsmanager.services.SportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportsServiceImpl implements SportsService {

    private SportRepository sportRepository;

    @Autowired
    public SportsServiceImpl(SportRepository sportRepository){
        this.sportRepository = sportRepository;
    }


    @Override
    public Sport save(Sport sport) {
        return sportRepository.save(sport);
    }

    @Override
    public void delete(Sport sport) {
        this.sportRepository.delete(sport);
    }

    @Override
    public List<Sport> findAll() {
        return this.sportRepository.findAll();
    }

    @Override
    public List<Sport> findBySportName(String sportName) {
        return this.sportRepository.findBySportName(sportName);
    }

    @Override
    public List<Sport> findByCountry_CountryName(String countryName) {
        return this.sportRepository.findByCountry_CountryName(countryName);
    }

    @Override
    public Optional<Sport> findBySportNameAndCountry_CountryName(String sportName, String countryName) {
        return this.sportRepository.findBySportNameAndCountry_CountryName(sportName,countryName);
    }

}
