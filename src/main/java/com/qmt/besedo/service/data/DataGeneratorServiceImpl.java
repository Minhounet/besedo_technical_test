package com.qmt.besedo.service.data;

import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static io.vavr.API.TODO;

@Log4j2
@RequiredArgsConstructor
@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {

    private final DataGeneratorConfiguration configuration;
    private final MessageDao messageDao;

    private final ApplicationContext context;

    @Override
    public void populateDatabase() {
        if (configuration.isPopulateDatabase()) {
            var attempt = Try.run(() -> {
                TODO("populate database with n entries");
            });
            attempt.onFailure(cause -> {
                log.error("Error when populating database", cause);
                SpringApplication.exit(context, () -> 1);
            });
        } else{
            log.info("Database is empty");
        }
    }


}
