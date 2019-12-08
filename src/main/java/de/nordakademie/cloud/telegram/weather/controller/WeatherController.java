package de.nordakademie.cloud.telegram.weather.controller;

import de.nordakademie.cloud.telegram.weather.model.WeatherReport;
import de.nordakademie.cloud.telegram.weather.service.OpenWeatherMapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import dto.DataTransferObject;
//import dto.Request;
//import dto.Response;
//import io.jsonwebtoken.JwtException;
//import security.JwsHelper;

@RestController
public class WeatherController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private OpenWeatherMapService openWeatherMapService;

    public WeatherController(OpenWeatherMapService openWeatherMapService) {
        this.openWeatherMapService = openWeatherMapService;
    }

//    @PostMapping("/full")
//    public ResponseEntity getWeather(@RequestBody String cityName) {
//        System.out.println("---------------------------------------------------------------");
//        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
//        if (weatherReport != null) {
//            return new ResponseEntity<>(weatherReport, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Unable to load weather from repository or API", HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping(value = "/temperature", produces = JSON, consumes = JSON)
    public ResponseEntity<String> getTemperature(@RequestBody String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        String response = weatherReport != null
                ? "Die Temperatur in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getTemperature()) + "°C"
                : "Leider konnte ich keine Angaben zur Temperatur für die angegebene Stadt finden. Prüfe, ob du sie richtig geschrieben hast.";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(value = "/humidity", produces = JSON, consumes = JSON)
    public ResponseEntity<String> getHumidity(@RequestBody String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        String response = weatherReport != null
                ? "Die Luftfeuchtigkeit in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getTemperature()) + "%"
                : "Leider konnte ich keine Angaben zur Luftfeuchtigkeit für die angegebene Stadt finden. Prüfe, ob du sie richtig geschrieben hast.";

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(value = "/visibility", produces = JSON, consumes = JSON)
    public ResponseEntity<String> getVisibility(@RequestBody String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        String response = weatherReport != null
                ? "Die Sichtweite in " + weatherReport.getCityName() + " beträgt " + Math.round(weatherReport.getVisibility()) + " Meter"
                : "Leider konnte ich keine Angaben zur Sichtweite für die angegebene Stadt finden. Prüfe, ob du sie richtig geschrieben hast.";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(value = "/wind", produces = JSON, consumes = JSON)
    public ResponseEntity<String> getWind(@RequestBody String cityName) {
        WeatherReport weatherReport = openWeatherMapService.getWeatherReport(cityName);
        String response = weatherReport != null
                ? "In " + weatherReport.getCityName() + " weht " + weatherReport.getWindDirectionOnCompass() + "Wind mit " + Math.round(weatherReport.getWindSpeed()) + "km/h"
                : "Leider konnte ich keine Angaben zum Wind für die angegebene Stadt finden. Prüfe, ob du sie richtig geschrieben hast.";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
