package de.nordakademie.cloud.telegram.weather.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Accessors(chain = true)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Indexed
    private String userInput;
    private String cityName;

}
