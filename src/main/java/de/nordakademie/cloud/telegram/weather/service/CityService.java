package de.nordakademie.cloud.telegram.weather.service;

import de.nordakademie.cloud.telegram.weather.model.City;
import de.nordakademie.cloud.telegram.weather.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public String getRealCityNameForUserInput(String cityName) {
        System.out.println("Looking up city name in repository");
        Optional<City> optionalCity = cityRepository.findFirstByUserInput(cityName);
        if (optionalCity.isPresent()) {
            String newCityName = optionalCity.get().getCityName(); // TODO Combine cityName = ... when removing debugging
            System.out.println("Found city name in repository (" + cityName + " -> " + newCityName + ")");
            cityName = newCityName;
        }
        return cityName;
    }

    public void saveCityAlias(String alias, String cityName) {
        System.out.println("Saving city name " + alias + " -> " + cityName);
        cityRepository.save(new City().setUserInput(alias).setCityName(cityName));
    }

}
