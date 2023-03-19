package com.qmt.besedo.service.export.csv;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "report")
@Data
public class CSVReportConfiguration {

    private String id = "id";
    private String vowelsCount = "Vowels count";
}

