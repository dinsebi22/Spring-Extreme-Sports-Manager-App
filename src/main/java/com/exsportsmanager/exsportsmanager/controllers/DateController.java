package com.exsportsmanager.exsportsmanager.controllers;

import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.services.CityService;
import com.exsportsmanager.exsportsmanager.services.CountryService;
import com.exsportsmanager.exsportsmanager.services.DateService;
import com.exsportsmanager.exsportsmanager.services.implementations.CityServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.CountryServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.DateServiceImpl;
import com.exsportsmanager.exsportsmanager.utils.SortDates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/dates")
public class DateController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
    private static final SortDates sortDates = new SortDates();
    private final DateService dateService;
    private final CityService cityService;

    @Autowired
    public DateController(DateServiceImpl dateServiceImpl, CityServiceImpl cityServiceImpl) {
        this.dateService = dateServiceImpl;
        this.cityService = cityServiceImpl;
    }

    @RequestMapping(path = "/getDates" , method = RequestMethod.GET)
    public List<Date> getAllDates(){
        List<Date> allDates =  this.dateService.findAllDates();
        allDates.sort(sortDates);
        return allDates;
    }

    @RequestMapping(path = "/getDatesOfCountry" , method = RequestMethod.GET)
    public List<Date> getDatesOfCountry(@RequestParam(name = "countryName") String countryName){
        List<Date> datesOfCountry = this.dateService.findBySport_Country_CountryName(countryName);
        datesOfCountry.sort(sortDates);
        return datesOfCountry;
    }

    @RequestMapping(path = "/getDatesOfSport" , method = RequestMethod.GET)
    public List<Date> getDatesOfSport(@RequestParam(name = "sportName") String sportName){
        List<Date> datesOfSport = this.dateService.findBySport_SportName(sportName);
        datesOfSport.sort(sortDates);
        return datesOfSport;
    }

    @RequestMapping(path = "/updateDateForSport" , method = RequestMethod.PUT)
    public Boolean updateDateForSport( @RequestParam(name = "countryName") String countryName,
                                            @RequestParam(name = "sportName") String sportName,
                                             @RequestParam(name = "startDay", required = false) Integer startDay,
                                             @RequestParam(name = "startMonth", required = false) String startMonth,
                                             @RequestParam(name = "endDay", required = false) Integer endDay,
                                             @RequestParam(name = "startMonth", required = false) String endMonth ){

        if( countryName != null && !StringUtils.isEmpty(countryName)){
            if(sportName != null && !StringUtils.isEmpty(sportName)){
                Optional<Date> date = this.dateService
                        .findBySport_Country_CountryNameAndSport_SportName(countryName, sportName);

                if(date.isPresent()){
                    Date updatedDate = date.get();

                    if(startDay != null){
                        updatedDate.setStartDay(startDay);
                    }
                    if(startMonth != null){
                        updatedDate.setStartMonth(startMonth);
                    }
                    if(endDay != null){
                        updatedDate.setEndDay(endDay);
                    }
                    if(endMonth != null){
                        updatedDate.setEndMonth(endMonth);
                    }
                    this.dateService.save(updatedDate);
                    logger.info("Date for Sport " + sportName + " from Country: " + countryName + " has Been Updated");
                    return true;
                }
                logger.warn("There are no dates for this " + sportName);
                return false;
            }
            logger.warn("Sport is not valid");
            return false;
        }
        logger.warn("Country is not Valid.");
        return false;
    }


}
