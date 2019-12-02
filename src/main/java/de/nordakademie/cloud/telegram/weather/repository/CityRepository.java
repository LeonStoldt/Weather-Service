package de.nordakademie.cloud.telegram.weather.repository;

import de.nordakademie.cloud.telegram.weather.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, String> {
    Optional<City> findFirstByUserInput(String userInput);
}
