package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class H2Dao implements MessageDao {

    private final MessageRepository messageRepository;

    @Override
    public Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject entity) {
        return Try.of(() -> {
            messageRepository.saveAndFlush(entity);
            return entity;
        });
    }

    @Override
    public Try<List<MessageDatabaseObject>> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value) {
        return null;
    }

    @Override
    public Try<List<MessageDatabaseObject>> getObjects() {
        return Try.of(messageRepository::findAll);
    }
}
