package com.exsportsmanager.exsportsmanager.controllers;

import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.models.Sport;
import com.exsportsmanager.exsportsmanager.services.CountryService;
import com.exsportsmanager.exsportsmanager.services.DateService;
import com.exsportsmanager.exsportsmanager.services.SportsService;
import com.exsportsmanager.exsportsmanager.services.implementations.CountryServiceImpl;
import com.exsportsmanager.exsportsmanager.services.implementations.SportsServiceImpl;
import com.exsportsmanager.exsportsmanager.utils.SortSportsByPriceAndName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/sports")
public class SportsController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;
    private final SportsService sportsService;
    private final DateService dateService;
    private static final SortSportsByPriceAndName sortSportsByPriceAndName = new SortSportsByPriceAndName();

    @Autowired
    public SportsController(CountryServiceImpl countryService, SportsServiceImpl sportsService, DateService dateService) {
        this.countryService = countryService;
        this.sportsService = sportsService;
        this.dateService = dateService;
    }

    @PutMapping(path = "/updateAveragePriceForSport")
    public boolean updateAveragePrice(
            @RequestParam("countryName")String countryName,
            @RequestParam("sportName") String sportName,
            @RequestParam("averagePrice") Float averagePrice){

        if( averagePrice != null){
            if(countryName != null && sportName != null){
                Sport sportToUpdate = this.sportsService.findBySportNameAndCountry_CountryName(sportName, countryName).get();
                if(sportToUpdate != null){
                    sportToUpdate.setAveragePrice(averagePrice);
                    this.sportsService.save(sportToUpdate);
                    logger.info("Sport: "+sportToUpdate.getSportName() + " was updated to have the following price:  "+averagePrice+ "  for Country with name " + countryName);
                    return true;
                }
                logger.warn("Specified sport in country does not exist.");
                return false;
            }
            logger.warn("Either country name or sport name is not valid");
            return false;
        }
        logger.warn("The Average price is not valid with value: "+averagePrice);
        return false;
    }

    @PostMapping(path = "/addDatesForSport")
    public boolean addDatesForSport(
            @RequestParam("countryName")String countryName,
            @RequestParam("sportName") String sportName,
            @RequestBody Date date){

            if(countryName != null && sportName != null){
                Sport sportToUpdate = this.sportsService.findBySportNameAndCountry_CountryName(sportName, countryName).get();
                if(sportToUpdate != null){
                    Set<Date> sportDates =  sportToUpdate.getDates();
                    if(sportDates.isEmpty()){
                        sportDates.add(date);
                        date.setSport(sportToUpdate);
                        this.dateService.save(date);
                        logger.info("Sport: "+sportToUpdate.getSportName() + " has had it's dates added for Country with name " + countryName);
                        return true;
                    }
                    for(Date d : sportDates){
                        if(date.equals(d)){
                            logger.warn("Date already Exists");
                            return false;
                        }
                    }
                    date.setSport(sportToUpdate);
                    sportDates.add(date);
                    logger.info("Sport: "+sportToUpdate.getSportName() + " has had it's dates updated for Country with name " + countryName);
                    this. dateService.save(date);
                    return true;
                }
                logger.warn("The sport you specified does not exist for that country");
                return false;
            }
            logger.warn("Either country name or sport name is not valid");
            return false;
    }

    @DeleteMapping(path = "/removeDateFromSport")
    public boolean removeDateFromSport(
            @RequestParam("countryName")String countryName,
            @RequestParam("sportName") String sportName,
            @RequestBody Date date){

        if(countryName != null && sportName != null){
            Optional<Sport> sportToUpdate = this.sportsService.findBySportNameAndCountry_CountryName(sportName, countryName);

            if(sportToUpdate.isPresent()){
                Set<Date> sportDates =  sportToUpdate.get().getDates();
                if(sportDates.isEmpty()){
                    logger.info("Sport: "+sportName + " has no dates for Country with name " + countryName);
                    return false;
                }
                for(Date d : sportDates){
                    if(date.equals(d)){
                        sportDates.remove(d);
                        this.sportsService.save(sportToUpdate.get());
                        this.dateService.delete(d);
                        logger.warn("Sport: "+sportName + " has had the following date removed: "+date.toString()+" for Country with name " + countryName);
                        return true;
                    }
                }
            }
            logger.warn("The sport you specified does not exist for that country");
            return false;
        }
        logger.warn("Either country name or sport name is not valid");
        return false;
    }

    @RequestMapping(path = "/all" , method = RequestMethod.GET)
    public List<Sport> getSports(){
        List<Sport> allSports = sportsService.findAll();
        allSports.sort(sortSportsByPriceAndName);
        return allSports;
    }
    @RequestMapping(path = "/getSportsForCountry" , method = RequestMethod.GET)
    public List<Sport> getSportsForCountry(@RequestParam(name = "countryName") String countryName){
        List<Sport> allSportsForCountry = this.sportsService.findByCountry_CountryName(countryName);
        allSportsForCountry.sort(sortSportsByPriceAndName);
        return allSportsForCountry;
    }



}
