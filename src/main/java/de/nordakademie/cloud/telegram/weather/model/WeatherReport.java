package de.nordakademie.cloud.telegram.weather.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Data
@Entity
@Accessors
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReport implements Serializable {
    public static final double TEMPERATURE_ABSOLUTE_ZERO = 273.15;
    @Id
    private Long id;
    private String countryCode;
    private Long sunrise;
    private Long sunset;

    @JsonProperty("sys")
    public void setCityInfo(Map<String, String> cityInfo) {
        this.id = (Long.parseLong(cityInfo.get("id")));
        this.countryCode = cityInfo.get("country");
        this.sunrise = (Long.parseLong(cityInfo.get("sunrise")));
        this.sunset = (Long.parseLong(cityInfo.get("sunset")));
    }

    @JsonAlias("name")
//    @Indexed
    private String cityName;

    @JsonAlias("dt")
    private Instant reportTimestamp;

    public void setReportTimestamp(String reportTimestamp) {
        this.reportTimestamp = Instant.ofEpochSecond(Long.parseLong(reportTimestamp));
    }

    // If no TTL is set, weather report expires immediately
    private Instant expiryTimestamp = Instant.now();

    public void setExpiryTimestampByTtl(Long minutes) {
        this.expiryTimestamp = this.reportTimestamp.plus(minutes, ChronoUnit.MINUTES);
    }

    @JsonAlias("cod")
    private Integer code;
    @JsonAlias("timezone")
    private Integer cityTimezone;
    private Integer visibility;
    private String base;

    private Double latitude;
    private Double longitude;

    @JsonProperty("coord")
    public void setLatLng(Map<String, String> coordinates) {
        this.latitude = (Double.parseDouble(coordinates.get("lat")));
        this.longitude = (Double.parseDouble(coordinates.get("lon")));
    }

    private Double temperature;
    private Double minimumTemperature;
    private Double maximumTemperature;
    private Integer pressure;
    private Integer humidity;

    @JsonProperty("main")
    public void setMeasurements(Map<String, String> measurements) {
        this.temperature = (double) Math.round((Double.parseDouble(measurements.get("temp")) - TEMPERATURE_ABSOLUTE_ZERO) * 100) / 100.0;
        this.minimumTemperature = (double) Math.round((Double.parseDouble(measurements.get("temp_min")) - TEMPERATURE_ABSOLUTE_ZERO) * 100) / 100.0;
        this.maximumTemperature = (double) Math.round((Double.parseDouble(measurements.get("temp_max")) - TEMPERATURE_ABSOLUTE_ZERO) * 100) / 100.0;
        this.pressure = (Integer.parseInt(measurements.get("pressure")));
        this.humidity = (Integer.parseInt(measurements.get("humidity")));
    }

    private Double windSpeed;
    private Integer windDirection;

    @JsonProperty("wind")
    public void setWind(Map<String, String> wind) {
        this.windSpeed = (Double.parseDouble(wind.get("speed")));
        if (wind.get("deg") != null) {
            this.windDirection = (Integer.parseInt(wind.get("deg")));
        }
    }

    public String getWindDirectionOnCompass() {
        if (windDirection >= 337.5 && windDirection <= 360 || windDirection >= 0 && windDirection < 22.5) {
            return "Nord-";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            return "Nord-Ost-";
        } else if (windDirection >= 67.5 && windDirection < 112.5) {
            return "Ost-";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            return "Süd-Ost-";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            return "Süd-";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            return "Süd-West-";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            return "West-";
        } else if (windDirection >= 292.5 && windDirection < 337.5) {
            return "Nord-West-";
        } else {
            return "variabler ";
        }
    }
}
