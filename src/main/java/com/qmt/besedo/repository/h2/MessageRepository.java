package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageDatabaseObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDatabaseObject, Long> {

    List<MessageDatabaseObject> findByEmail(String email);
    List<MessageDatabaseObject> findByEmailStartsWith(String email);
    List<MessageDatabaseObject> findByEmailContains(String email);

    List<MessageDatabaseObject> findByBody(String body);
    List<MessageDatabaseObject> findByBodyStartsWith(String body);
    List<MessageDatabaseObject> findByBodyContains(String body);


    List<MessageDatabaseObject> findById(String id);
    List<MessageDatabaseObject> findByIdStartsWith(String id);
    List<MessageDatabaseObject> findByIdContains(String id);

    List<MessageDatabaseObject> findByTitle(String title);
    List<MessageDatabaseObject> findByTitleStartsWith(String title);
    List<MessageDatabaseObject> findByTitleContains(String title);


}
