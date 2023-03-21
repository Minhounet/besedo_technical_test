package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageDatabaseObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface to request the H2 database.
 */
public interface MessageRepository extends JpaRepository<MessageDatabaseObject, Long> {

    List<MessageDatabaseObject> findByEmail(String email);
    int countByEmail(String email);
    List<MessageDatabaseObject> findByEmailStartsWith(String email);
    int countByEmailStartsWith(String email);
    List<MessageDatabaseObject> findByEmailContains(String email);
    int countByEmailContains(String email);

    List<MessageDatabaseObject> findByBody(String body);
    int countByBody(String body);
    List<MessageDatabaseObject> findByBodyStartsWith(String body);
    int countByBodyStartsWith(String body);
    List<MessageDatabaseObject> findByBodyContains(String body);
    int countByBodyContains(String body);

    List<MessageDatabaseObject> findById(String id);
    int countById(String body);
    List<MessageDatabaseObject> findByIdStartsWith(String id);
    int countByIdStartsWith(String body);
    List<MessageDatabaseObject> findByIdContains(String id);
    int countByIdContains(String body);

    List<MessageDatabaseObject> findByTitle(String title);
    int countByTitle(String body);
    List<MessageDatabaseObject> findByTitleStartsWith(String title);
    int countByTitleStartsWith(String body);
    List<MessageDatabaseObject> findByTitleContains(String title);
    int countByTitleContains(String body);
}
