package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageDatabaseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface to request the H2 database.
 */
public interface MessageRepository extends JpaRepository<MessageDatabaseObject, Long> {

    List<MessageDatabaseObject> findByEmail(String email, Pageable pageable);
    int countByEmail(String email);
    List<MessageDatabaseObject> findByEmailStartsWith(String email, Pageable pageable);
    int countByEmailStartsWith(String email);
    List<MessageDatabaseObject> findByEmailContains(String email, Pageable pageable);
    int countByEmailContains(String email);

    List<MessageDatabaseObject> findByBody(String body, Pageable pageable);
    int countByBody(String body);
    List<MessageDatabaseObject> findByBodyStartsWith(String body, Pageable pageable);
    int countByBodyStartsWith(String body);
    List<MessageDatabaseObject> findByBodyContains(String body, Pageable pageable);
    int countByBodyContains(String body);

    List<MessageDatabaseObject> findById(String id, Pageable pageable);
    int countById(String body);
    List<MessageDatabaseObject> findByIdStartsWith(String id, Pageable pageable);
    int countByIdStartsWith(String body);
    List<MessageDatabaseObject> findByIdContains(String id, Pageable pageable);
    int countByIdContains(String body);

    List<MessageDatabaseObject> findByTitle(String title, Pageable pageable);
    int countByTitle(String body);
    List<MessageDatabaseObject> findByTitleStartsWith(String title, Pageable pageable);
    int countByTitleStartsWith(String body);
    List<MessageDatabaseObject> findByTitleContains(String title, Pageable pageable);
    int countByTitleContains(String body);
}
