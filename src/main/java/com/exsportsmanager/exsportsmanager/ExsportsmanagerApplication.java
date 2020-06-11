package com.exsportsmanager.exsportsmanager;

import com.exsportsmanager.exsportsmanager.models.City;
import com.exsportsmanager.exsportsmanager.models.Country;
import com.exsportsmanager.exsportsmanager.models.Date;
import com.exsportsmanager.exsportsmanager.models.Sport;
import com.exsportsmanager.exsportsmanager.models.enums.Regions;
import com.exsportsmanager.exsportsmanager.repositories.CityRepository;
import com.exsportsmanager.exsportsmanager.repositories.CountryRepository;
import com.exsportsmanager.exsportsmanager.repositories.DateRepository;
import com.exsportsmanager.exsportsmanager.repositories.SportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExsportsmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExsportsmanagerApplication.class, args);
	}


	@Bean
	public CommandLineRunner mappingDemo(SportRepository sportRepository,
										 CountryRepository countryRepository ,
										 DateRepository dateRepository,
										 CityRepository cityRepository

	) {
		return args -> {
			Country c1 = new Country();
			c1.setCountryName("Romania");
			c1.setRegion(Regions.EUROPE);

			Country c2 = new Country();
			c2.setCountryName("Italy");
			c2.setRegion(Regions.EUROPE);

			Country c3 = new Country();
			c3.setCountryName("Morocco");
			c3.setRegion(Regions.AFRICA);

			countryRepository.save(c1);
			countryRepository.save(c2);
			countryRepository.save(c3);

			City city1 = new City();
			city1.setCityName("Brasov");
			city1.setCountry(c1);
			City city2 = new City();
			city2.setCityName("Constanta");
			city2.setCountry(c1);
			City city3 = new City();
			city3.setCityName("Iasi");
			city3.setCountry(c1);

			Sport s1 = new Sport();
			s1.setSportName("SnowBoarding");
			s1.setAveragePrice(50F);
			s1.setCountry(c1);
			s1.setCity(city1);

			Sport s2 = new Sport();
			s2.setSportName("Skiing");
			s2.setAveragePrice(50F);
			s2.setCountry(c1);
			s2.setCity(city1);

			Sport s3 = new Sport();
			s3.setSportName("Diving");
			s3.setAveragePrice(100F);
			s3.setCountry(c2);
			s3.setCity(city2);

			Sport s4 = new Sport();
			s4.setSportName("Running");
			s4.setAveragePrice(5F);
			s4.setCountry(c3);
			s4.setCity(city2);

			Sport s5 = new Sport();
			s5.setSportName("Diving");
			s5.setAveragePrice(100F);
			s5.setCountry(c1);
			s5.setCity(city3);



			Sport s6 = new Sport();
			s6.setSportName("Diving");
			s6.setAveragePrice(120F);
			s6.setCountry(c3);
			s6.setCity(city3);

			Sport s7 = new Sport();
			s7.setSportName("Skiing");
			s7.setAveragePrice(30F);
			s7.setCountry(c2);
			s7.setCity(city3);

			Date d1 = new Date();
			d1.setStartDay(1);
			d1.setStartMonth("May");
			d1.setEndDay(30);
			d1.setEndMonth("August");
			d1.setSport(s3);

			Date d2 = new Date();
			d2.setStartDay(1);
			d2.setStartMonth("November");
			d2.setEndDay(30);
			d2.setEndMonth("January");
			d2.setSport(s2);

			Date d3 = new Date();
			d3.setStartDay(1);
			d3.setStartMonth("January");
			d3.setEndDay(30);
			d3.setEndMonth("February");
			d3.setSport(s4);

			cityRepository.save(city1);
			cityRepository.save(city2);
			cityRepository.save(city3);

			sportRepository.save(s1);
			sportRepository.save(s2);
			sportRepository.save(s3);
			sportRepository.save(s4);
			sportRepository.save(s5);
			sportRepository.save(s6);
			sportRepository.save(s7);



			dateRepository.save(d1);
			dateRepository.save(d2);
			dateRepository.save(d3);


		};
	}

}
