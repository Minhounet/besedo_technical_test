package com.qmt.besedo.repository;

import com.qmt.besedo.repository.h2.H2Dao;
import com.qmt.besedo.repository.h2.MessageRepository;
import com.qmt.besedo.repository.list.ObjectsListDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
    @Bean
    @ConditionalOnProperty(name="database.type", havingValue="h2")
    MessageDao getH2Dao(MessageRepository messageRepository) {
        return new H2Dao(messageRepository);
    }

    @Bean
    @ConditionalOnProperty(name="database.type", havingValue="javaList", matchIfMissing = true)
    MessageDao getObjectsListDao(){
        return new ObjectsListDao();
    }
}
