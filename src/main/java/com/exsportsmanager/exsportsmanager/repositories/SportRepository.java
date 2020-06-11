package com.exsportsmanager.exsportsmanager.repositories;
import com.exsportsmanager.exsportsmanager.models.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportRepository extends JpaRepository<Sport, Integer> {

    Sport save(Sport Sport);

    void delete(Sport Sport);

    List<Sport> findAll();

    List<Sport> findBySportName(String sportName);

    List<Sport> findByCountry_CountryName(String countryName);

    Optional<Sport> findBySportNameAndCountry_CountryName(String sportName, String countryName);

}
