package com.exsportsmanager.exsportsmanager.repositories;

import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country save(Country Country);

    void delete(Country Country);

    List<Country> findAll();

    Optional<Country> findByCountryName(String countryName);

    List<Country> findByRegion(Regions region);

    List<Country> findBySports_SportName(String sportName);

    Optional<Country> findByCities_CityName(String cityName);
}
