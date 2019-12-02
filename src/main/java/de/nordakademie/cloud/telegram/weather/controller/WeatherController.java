package de.nordakademie.cloud.telegram.weather.controller;

import de.nordakademie.cloud.telegram.weather.model.WeatherReport;
import de.nordakademie.cloud.telegram.weather.service.OpenWeatherMapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import dto.DataTransferObject;
//import dto.Request;
//import dto.Response;
//import io.jsonwebtoken.JwtException;
//import security.JwsHelper;

@RestController
public class WeatherController {

    private OpenWeatherMapService openWeatherMapService;

    public WeatherController(OpenWeatherMapService openWeatherMapService) {
        this.openWeatherMapService = openWeatherMapService;
    }

//    @GetMapping("/full")
//    public ResponseEntity getWeather(@RequestParam String cityName) {
//        System.out.println("---------------------------------------------------------------");
//        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
//        if (weatherReport != null) {
//            return new ResponseEntity<>(weatherReport, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Unable to load weather from repository or API", HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/temperature")
    public ResponseEntity getTemperature(@RequestParam String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        if (weatherReport != null) {
            return new ResponseEntity<>("Die Temperatur in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getTemperature()) + "°C", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/humidity")
    public ResponseEntity getHumidity(@RequestParam String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        if (weatherReport != null) {
            return new ResponseEntity<>("Die Luftfeuchtigkeit in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getTemperature()) + "%", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/visibility")
    public ResponseEntity getVisibility(@RequestParam String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        if (weatherReport != null) {
            return new ResponseEntity<>("Die Sichtweite in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getVisibility()) + " Meter", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/wind")
    public ResponseEntity getWind(@RequestParam String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        if (weatherReport != null) {
            return new ResponseEntity<>("In " + weatherReport.getCityName() + " weht " + weatherReport.getWindDirectionOnCompass() + "Wind mit " + Math.round(weatherReport.getWindSpeed()) + "km/h", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
