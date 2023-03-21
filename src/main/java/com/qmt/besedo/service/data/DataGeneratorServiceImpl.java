package com.qmt.besedo.service.data;

import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomStringUtils.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {

    private final DataGeneratorConfiguration configuration;
    private final MessageDao messageDao;
    private final ApplicationContext context;
    private final Random random = new Random();

    @Override
    public void populateDatabase() {
        if (configuration.isPopulateDatabase()) {
            int dataCount = configuration.getDataCount();
            log.info("Populate database with " + dataCount +" elements");
            var attempt = Try.run(() -> {
                List<Future<MessageDatabaseObject>> futures = IntStream
                        .range(0, dataCount)
                        .mapToObj(ignored -> Future.of(() -> messageDao.injectMessage(buildRandomMessage()).get()))
                        .toList();
                Future.sequence(futures).await();
            });
            attempt.onFailure(cause -> {
                log.error("Error when populating database", cause);
                SpringApplication.exit(context, () -> 1);
            });
        } else {
            log.info("Database is empty");
        }
    }

    private MessageDatabaseObject buildRandomMessage() {
        var randomId = randomAlphanumeric(1, 101);
        var randomMail = randomAlphabetic(10) + "@" + randomDomain();
        var randomTitle = randomAlphanumeric(1, 300);
        var randomBody = randomAlphanumeric(1, 10000);
        var messageDatabaseObject = new MessageDatabaseObject();
        messageDatabaseObject.setId(randomId);
        messageDatabaseObject.setEmail(randomMail);
        messageDatabaseObject.setTitle(randomTitle);
        messageDatabaseObject.setBody(randomBody);
        return messageDatabaseObject;
    }

    private String randomDomain() {
        var existingDomains = List.of("gmail.com", "hotmail.com", "yahoo.fr");
        int nb = random.nextInt(4);
        if (nb == 3) {
            return randomAlphabetic(5) + "besedo.com";
        } else {
            return existingDomains.get(nb);
        }
    }


}
