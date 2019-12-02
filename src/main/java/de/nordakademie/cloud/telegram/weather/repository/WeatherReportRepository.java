package de.nordakademie.cloud.telegram.weather.repository;

import de.nordakademie.cloud.telegram.weather.model.WeatherReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface WeatherReportRepository extends CrudRepository<WeatherReport, Long> {
    Optional<WeatherReport> findFirstByCityNameAndReportTimestampAfterOrderByReportTimestampDesc(String cityName, Instant instant);
}
