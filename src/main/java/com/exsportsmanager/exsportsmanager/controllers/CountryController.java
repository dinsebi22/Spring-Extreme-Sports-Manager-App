package com.exsportsmanager.exsportsmanager.controllers;

import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.models.Sport;
import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import com.exsportsmanager.exsportsmanager.services.CityService;
import com.exsportsmanager.exsportsmanager.services.CountryService;
import com.exsportsmanager.exsportsmanager.services.SportsService;
import com.exsportsmanager.exsportsmanager.services.implementations.CityServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.CountryServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.SportsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;
    private final CityService cityService;
    private final SportsService sportsService;

    @Autowired
    public CountryController(CountryServiceImpl countryService, CityServiceImpl cityServiceImpl, SportsServiceImpl sportsService) {
        this.countryService = countryService;
        this.cityService = cityServiceImpl;
        this.sportsService = sportsService;
    }

    @PostMapping(path = "/addCountry")
    public boolean addCountry(@RequestParam("countryName") String countryName, @RequestParam("regionName") String regionName) {
        if (countryName != null) {
            try {
                Country possibleExistingCountry = this.countryService.findByCountryName(countryName).get();
                logger.warn("Country with name: " + possibleExistingCountry.getCountryName() + " already Exists.");
                return false;
            } catch (NoSuchElementException e) {
                Country newCountry = new Country();
                newCountry.setCountryName(countryName);
                try {
                    Regions region = Regions.valueOf(regionName.toUpperCase());
                    newCountry.setRegion(region);
                } catch (IllegalArgumentException ex) {
                    logger.info("Region is not valid");
                    return false;
                }
                this.countryService.save(newCountry);
                logger.info("Country with name " + countryName + " added in region "+regionName);
                return true;
            }
        }
        logger.warn("Country name is not valid");
        return false;
    }

    @DeleteMapping(path = "/removeCountry")
    public boolean removeCountry(@RequestParam("countryName") String countryName) {
        if (countryName != null) {
            try {
                Country country = this.countryService.findByCountryName(countryName).get();
                this.countryService.delete(country);
                logger.info("Country with name " + countryName + " removed.");
                return true;
            } catch (NoSuchElementException e) {
                logger.warn("Country does not Exist in the database.");
                return false;
            }
        }
        logger.warn("Country name is not valid");
        return false;
    }

    @PostMapping(path = "/addSportForCountry")
    public boolean addSportForCountry(@RequestParam("countryName") String countryName,
                                      @RequestParam("cityName") String cityName,
                                      @RequestParam("sportName") String sportName,
                                      @RequestParam(value = "averagePrice", required = false) Float averagePrice) {
        if (countryName != null && sportName != null && cityName != null) {

            Optional<Country> country = this.countryService.findByCountryName(countryName);
            boolean isCountryPresent = country.isPresent();
            Optional<City> city = this.cityService.findByCityName(cityName);
            boolean isCityPresent = city.isPresent();

            if (isCountryPresent) {
                Set<Sport> countrySports = country.get().getSports();
                for (Sport s : countrySports) {
                    if (s.getSportName().equals(sportName)) {
                        logger.warn("Sport already Exist in Country.");
                        if ((averagePrice == null)) {
                            s.setAveragePrice(0F);
                        } else {
                            s.setAveragePrice(averagePrice);
                            this.sportsService.save(s);
                            logger.info("Average price was changed to " + averagePrice);
                        }
                        return false;
                    }
                }
                if (isCityPresent) {
                    Set<Sport> citySports = country.get().getSports();
                    for (Sport s : citySports) {
                        if (s.getSportName().equals(sportName)) {
                            logger.warn("Sport already Exist in City.");
                            if ((averagePrice == null)) {
                                s.setAveragePrice(0F);
                            } else {
                                s.setAveragePrice(averagePrice);
                                this.sportsService.save(s);
                                logger.info("Average price was changed to " + averagePrice);
                            }
                            return false;
                        }
                    }
                } else {
                    logger.warn("There are no cities registered in country with name " + countryName);
                    return false;
                }
            } else {
                logger.warn("Country does not Exist.");
                return false;
            }

            Sport newSport = new Sport();
            newSport.setSportName(sportName);
            if ((averagePrice == null)) {
                newSport.setAveragePrice(0F);
            } else {
                newSport.setAveragePrice(averagePrice);
            }

            newSport.setCountry(country.get());
            newSport.setCity(city.get());
            country.get().getSports().add(newSport);
            this.sportsService.save(newSport);
            logger.info("Sport: " + newSport.getSportName() + " was added to Country with name " + countryName);
            return true;

        }

        logger.warn("Either country name or sport name is not valid");
        return false;
    }

    @DeleteMapping(path = "/removeSportFromCountry")
    public boolean removeSportFromCountry(@RequestParam("countryName") String countryName,
                                          @RequestParam("sportName") String sportName) {
        if (countryName != null && sportName != null) {
            Optional<Country> countryToUpdate = this.countryService.findByCountryName(countryName);
            boolean isCountryPresent = countryToUpdate.isPresent();

            boolean sportRemovedFromCountry = false;
            boolean sportRemovedFromCity = false;

            if (isCountryPresent) {
                Set<Sport> sports = countryToUpdate.get().getSports();
                for (Sport s : sports) {
                    if (s.getSportName().equals(sportName)) {
                        sports.remove(s);
                        this.countryService.save(countryToUpdate.get());
                        sportRemovedFromCountry = true;
                        logger.info("Sport: " + sportName + " was removed from Country with name " + countryName);
                        break;
                    }
                }
                Set<City> cities = countryToUpdate.get().getCities();
                for (City c : cities) {
                    Set<Sport> citySports = c.getSports();
                    if (citySports.removeIf(s -> s.getSportName().equals(sportName))) {
                        sportRemovedFromCity = true;
                    }
                    cityService.save(c);
                }
                if (sportRemovedFromCountry && sportRemovedFromCity) {
                    logger.info("Sport: " + sportName + " was removed from Country and Cities in Country with name " + countryName);
                    return true;
                } else if (sportRemovedFromCountry) {
                    logger.info("Sport: " + sportName + " was removed from Country with name " + countryName);
                    return true;
                } else {
                    logger.info("Sport: " + sportName + " does not exist for Country with name " + countryName);
                    return false;
                }
            }
            logger.warn("Country does not Exist.");
            return false;
        }
        logger.warn("Either country name or sport name is not valid");
        return false;
    }

    @GetMapping(path = "/getSportsByCountry")
    public Optional<Country> getSportsByCountry(@RequestParam(name = "countryName") String countryName) {
        return countryService.findByCountryName(countryName);
    }

    @GetMapping(path = "/getAllCountries")
    public List<Country> getCountries() {
        return countryService.findAll();
    }

    @GetMapping(path = "/getCountryByRegion")
    public List<Country> getCountryByRegion(@RequestParam(name = "regionName") String regionName) {
        Regions region;
        try {
            region = Regions.valueOf(regionName);
            return countryService.findByRegion(region);
        } catch (IllegalArgumentException ex) {
            logger.info("Region is not valid");
            return Collections.emptyList();
        }
    }

    @GetMapping(path = "/getCitiesFromCountry")
    public Set<City> getCitiesFromCountry(@RequestParam(name = "countryName") String countryName) {
        if (countryName != null){
            Optional<Country> country = this.countryService.findByCountryName(countryName);
            if(country.isPresent()){
                return country.get().getCities();
            }
            logger.info("Country is not registered. ");
            return Collections.emptySet();
        }
        return Collections.emptySet();
    }


}
