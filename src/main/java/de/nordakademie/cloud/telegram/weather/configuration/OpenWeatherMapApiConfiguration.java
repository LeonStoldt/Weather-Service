package de.nordakademie.cloud.telegram.weather.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("openweathermap.api")
public class OpenWeatherMapApiConfiguration {

    @NotEmpty
    private String key;

    @NotEmpty
    private String version;

    @NotEmpty
    private String language;

    @NotEmpty
    private Long cacheTtl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(Long cacheTtl) {
        this.cacheTtl = cacheTtl;
    }
}
