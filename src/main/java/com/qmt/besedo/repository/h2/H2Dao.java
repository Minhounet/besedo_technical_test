package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class H2Dao implements MessageDao {

    private final MessageRepository messageRepository;

    @Override
    public Try<Message> injectMessage(Message message) {
        return Try.of(() -> {
            messageRepository.saveAndFlush(message.toDatabaseObject());
            return message;
        });
    }

    @Override
    public Try<List<Message>> getObjects() {
        return Try.of(() -> messageRepository
                .findAll().stream()
                .map(MessageDatabaseObject::toMessage)
                .toList());
    }
}
