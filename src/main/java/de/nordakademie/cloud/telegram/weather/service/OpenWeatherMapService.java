package de.nordakademie.cloud.telegram.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.cloud.telegram.weather.configuration.OpenWeatherMapApiConfiguration;
import de.nordakademie.cloud.telegram.weather.model.WeatherReport;
import de.nordakademie.cloud.telegram.weather.repository.WeatherReportRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
public class OpenWeatherMapService {

    private OpenWeatherMapApiConfiguration openWeatherMapApiConfiguration;
    private WeatherReportRepository weatherReportRepository;
    private CityService cityService;

    public OpenWeatherMapService(OpenWeatherMapApiConfiguration openWeatherMapApiConfiguration, WeatherReportRepository weatherReportRepository, CityService cityService) {
        this.openWeatherMapApiConfiguration = openWeatherMapApiConfiguration;
        this.weatherReportRepository = weatherReportRepository;
        this.cityService = cityService;
    }

    // Try to get valid weather report from cache or request current weather from API
    public WeatherReport getWeatherReport(String cityName) {
        String cityNameOptimizedForQueries = cityService.getRealCityNameForUserInput(cityName);
        System.out.println("Looking up weather report for " + cityNameOptimizedForQueries + " in repository");

        return weatherReportRepository.findFirstByCityNameAndReportTimestampAfterOrderByReportTimestampDesc(
                cityNameOptimizedForQueries,
                Instant.now().minus(
                        openWeatherMapApiConfiguration.getCacheTtl(),
                        ChronoUnit.MINUTES
                )
        ).orElseGet(() ->
                requestWeatherReportFromApi(cityNameOptimizedForQueries)
        );
    }

    private WeatherReport requestWeatherReportFromApi(String cityName) {
        System.out.println("No weather report found in repository, calling API");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/" + openWeatherMapApiConfiguration.getVersion() +
                        "/weather?q=" + cityName +
                        "&APPID=" + openWeatherMapApiConfiguration.getKey() +
                        "&lang=" + openWeatherMapApiConfiguration.getLanguage())
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() == 200 && response.body() != null) {
                WeatherReport weatherReport = new ObjectMapper().readValue(
                        Objects.requireNonNull(response.body()).string(),
                        WeatherReport.class
                );
                weatherReport.setExpiryTimestampByTtl(openWeatherMapApiConfiguration.getCacheTtl());

                cityService.saveCityAlias(cityName, weatherReport.getCityName());

                weatherReportRepository.save(weatherReport);
                return weatherReport;
            } else {
                // TODO Log failed cityNames so that the API won't be called again for that cityName
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
