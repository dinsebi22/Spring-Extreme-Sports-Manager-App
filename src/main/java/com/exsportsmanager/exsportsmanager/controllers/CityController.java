package com.exsportsmanager.exsportsmanager.controllers;


import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.models.Sport;
import com.exsportsmanager.exsportsmanager.services.CityService;
import com.exsportsmanager.exsportsmanager.services.CountryService;
import com.exsportsmanager.exsportsmanager.services.SportsService;
import com.exsportsmanager.exsportsmanager.services.implementations.CityServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.CountryServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.SportsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(path = "/cities")
@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
    private final CityService cityService;
    private final SportsService sportsService;
    private final CountryService countryService;

    @Autowired
    public CityController(CityServiceImpl cityServiceImpl, SportsServiceImpl sportsServiceImpl, CountryServiceImpl countryServiceImpl) {
        this.cityService = cityServiceImpl;
        this.sportsService = sportsServiceImpl;
        this.countryService = countryServiceImpl;
    }

    @GetMapping("/all")
    public List<City> getAllCities() {
        return this.cityService.findAll();
    }

    @GetMapping("/getCityInfo")
    public Optional<City> getCityForName(@RequestParam("cityName") String cityName) {
        Optional<City> city = this.cityService.findByCityName(cityName);
        if (city.isPresent()) {
            return city;
        }
        logger.info("City with name  " + cityName + " does not exist");
        return Optional.empty();
    }

    @DeleteMapping("/removeSportFromCity")
    public boolean removeSportFromCity(@RequestParam("cityName") String cityName, @RequestParam("sportName") String sportName) {
        Optional<City> city = this.cityService.findByCityName(cityName);
        boolean isCityPresent = city.isPresent();

        if (isCityPresent) {
            if (sportName != null && !StringUtils.isEmpty(sportName)) {
                City cityToUpdate = city.get();
                Set<Sport> sports = cityToUpdate.getSports();
                for (Sport s : sports) {
                    if (s.getSportName().equals(sportName)) {
                        sports.remove(s);
                        this.cityService.save(cityToUpdate);
                        logger.info("Sport: " + sportName + " was removed from city with name " + cityName);
                        return true;
                    }
                }
                logger.info("Sport with name " + sportName + " does not exist for " + cityName);
                return false;
            }
            logger.info("Sport name is not valid");
            return false;
        }
        logger.info("City with name " + cityName + " does not exist");
        return false;
    }

    @PostMapping(path = "/addSportForCity")
    public boolean addSportForCity(
            @RequestParam("cityName") String cityName,
            @RequestParam("sportName") String sportName,
            @RequestParam(value = "averagePrice", required = false) Float averagePrice) {

        if (averagePrice == null) {
            averagePrice = 0F;
        }

        if (sportName != null && !StringUtils.isEmpty(sportName) && cityName != null && !StringUtils.isEmpty(cityName)) {

            Optional<City> city = this.cityService.findByCityName(cityName);
            boolean isCityPresent = city.isPresent();

            if (isCityPresent) {
                Set<Sport> citySports = city.get().getSports();
                for (Sport s : citySports) {
                    if (s.getSportName().equals(sportName)) {
                        logger.warn("Sport already Exist in City.");
                        return false;
                    }
                }
            } else {
                logger.warn("There are no cities registered with that name in this Country");
                return false;
            }

            Set<Sport> countrySports = city.get().getCountry().getSports();
            for (Sport s : countrySports) {
                if (s.getSportName().equals(sportName) && !s.getAveragePrice().equals(averagePrice)) {

                    Sport newSport = new Sport();
                    newSport.setSportName(sportName);
                    newSport.setAveragePrice(averagePrice);

                    newSport.setCountry(city.get().getCountry());
                    newSport.setCity(city.get());
                    city.get().getCountry().getSports().add(newSport);

                    this.sportsService.save(newSport);

                    logger.info("Sport with name " + sportName + " with average price " + averagePrice + " added");
                    return true;
                }
            }

            logger.warn("Sport alreasy exist in country with name: " + city.get().getCountry().getCountryName());
            return false;
        }
        logger.warn("Either country name or sport name or city name is not valid");
        return false;

    }


    @RequestMapping(path = "/updateSportForCity", method = RequestMethod.PUT)
    public Boolean updateDateForSport(@RequestParam(name = "cityName") String cityName,
                                      @RequestParam(name = "sportName") String sportName,
                                      @RequestParam(name = "averagePrice", required = true) Float averagePrice) {

        if (sportName != null && !StringUtils.isEmpty(sportName)) {
            if (cityName != null && !StringUtils.isEmpty(cityName)) {

                Optional<City> cityToUpdate = this.cityService.findByCityName(cityName);
                boolean isCityToUpdate = cityToUpdate.isPresent();
                if (isCityToUpdate) {
                    Set<Sport> citySports = cityToUpdate.get().getSports();
                    for (Sport s : citySports) {
                        if (s.getSportName().equals(sportName)) {
                            s.setAveragePrice(averagePrice);
                            this.sportsService.save(s);
                        }
                    }

                    Optional<Country> cityCountry = this.countryService.findByCities_CityName(cityName);
                    if (cityCountry.isPresent()) {
                        Optional<Sport> countrySport = sportsService.findBySportNameAndCountry_CountryName(sportName, cityCountry.get().getCountryName());
                        if (countrySport.isPresent()) {
                            Sport updateCountrySport = countrySport.get();
                            updateCountrySport.setAveragePrice(averagePrice);
                            this.sportsService.save(updateCountrySport);
                            logger.info("Average price was changed to " + averagePrice + " for city with name: " + cityName + " in country: " + cityCountry.get().getCountryName());
                            return true;
                        }
                        logger.info("Average price was changed to " + averagePrice + " for city with name: " + cityName + " but no country was available");
                        return false;
                    }
                }
                logger.info("Spor with name: " + sportName + " does not exist for city with name: " + cityName);
                return false;
            }
            logger.warn("City name is not valid");
            return false;
        }
        logger.warn("Sport is not valid");
        return false;
    }

    @PostMapping(path = "/addCityToCountry")
    public boolean addCityToCountry(@RequestParam("countryName") String countryName, @RequestParam("cityName") String cityName) {
        if (countryName != null) {
            try {
                Country existingCountry = this.countryService.findByCountryName(countryName).get();
                for (City c : existingCountry.getCities()) {
                    if (c.getCityName().equals(cityName)) {
                        logger.info("City with name " + cityName + " Already Exists.");
                        return false;
                    }
                }
                City newCity = new City();
                newCity.setCityName(cityName);
                newCity.setCountry(existingCountry);
                existingCountry.getCities().add(newCity);
                this.cityService.save(newCity);
                this.countryService.save(existingCountry);
                return true;
            } catch (NoSuchElementException e) {
                logger.info("Country with name " + countryName + " does not exist.");
                return false;
            }
        }
        logger.warn("Country name is not valid");
        return false;
    }

    @DeleteMapping(path = "/removeCityFromCountry")
    public boolean removeCityFromCountry(@RequestParam("countryName") String countryName, @RequestParam("cityName") String cityName) {
        if (countryName != null && !StringUtils.isEmpty(countryName)) {
            try {
                Country existingCountry = this.countryService.findByCountryName(countryName).get();
                if (!StringUtils.isEmpty(cityName)) {
                    for (City c : existingCountry.getCities()) {
                        if (c.getCityName().equals(cityName)) {

                            existingCountry.getCities().remove(c);
                            cityService.delete(c);
                            countryService.save(existingCountry);
                            logger.info("City with name " + cityName + " was removed from country with name: " + existingCountry.getCountryName());
                            return true;
                        }
                    }
                }
                logger.info("City with name " + cityName + " does not exist.");
                return false;
            } catch (NoSuchElementException e) {
                logger.info("Country with name " + countryName + " does not exist.");
                return false;
            }
        }
        logger.warn("Country name is not valid");
        return false;
    }

}
