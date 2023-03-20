package com.qmt.besedo.repository.h2;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class H2Dao implements MessageDao {
    private final MessageRepository messageRepository;
    private final Map<Tuple2<MessageAttributeName, SearchOperator>, Function<String, List<MessageDatabaseObject>>> findMethods = new HashMap<>();

    public H2Dao(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject entity) {
        return Try.of(() -> {
            messageRepository.saveAndFlush(entity);
            return entity;
        });
    }

    @Override
    public Try<List<MessageDatabaseObject>> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value) {
        return Try.of(() -> findMethods.get(Tuple.of(attributeName, operator)).apply(value));
    }

    @PostConstruct
    public void initFindMethods() {
        findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.EQUALS), messageRepository::findById);
        findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.STARTS_WITH), messageRepository::findByIdStartsWith);
        findMethods.put(Tuple.of(MessageAttributeName.ID, SearchOperator.CONTAINS), messageRepository::findByIdContains);
        findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.EQUALS), messageRepository::findByEmail);
        findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.STARTS_WITH), messageRepository::findByEmailStartsWith);
        findMethods.put(Tuple.of(MessageAttributeName.EMAIL, SearchOperator.CONTAINS), messageRepository::findByEmailContains);
        findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.EQUALS), messageRepository::findByTitle);
        findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.STARTS_WITH), messageRepository::findByTitleStartsWith);
        findMethods.put(Tuple.of(MessageAttributeName.TITLE, SearchOperator.CONTAINS), messageRepository::findByTitleContains);
        findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.EQUALS), messageRepository::findByBody);
        findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.STARTS_WITH), messageRepository::findByBodyStartsWith);
        findMethods.put(Tuple.of(MessageAttributeName.BODY, SearchOperator.CONTAINS), messageRepository::findByBodyContains);
    }

    @Override

    public Try<List<MessageDatabaseObject>> getObjects() {
        return Try.of(messageRepository::findAll);
    }
}
