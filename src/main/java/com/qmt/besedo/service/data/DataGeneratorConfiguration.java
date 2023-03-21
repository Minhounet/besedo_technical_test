package com.qmt.besedo.service.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "data")
@Data
public class DataGeneratorConfiguration {

    private boolean populateDatabase = false;

    private int dataCount = 1000;

}
