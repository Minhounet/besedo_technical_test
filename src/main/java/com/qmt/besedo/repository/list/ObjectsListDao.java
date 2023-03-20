package com.qmt.besedo.repository.list;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * Basic database where objects are stored in {@link CopyOnWriteArrayList} to handle concurrency.
 */
@Repository
public class ObjectsListDao implements MessageDao {

    private final List<MessageDatabaseObject> objects = new CopyOnWriteArrayList<>();

    @Override
    public Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject entity) {
        return Try.of(() -> {
            objects.add(entity);
            return entity;
        });
    }

    @Override
    public Try<List<MessageDatabaseObject>> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value) {
        return Try.of(() -> {
            Predicate<MessageDatabaseObject> doesValueMatch = messageDatabaseObject -> {
                String fieldValue = attributeName.getMessageDatabaseObjectAttributeFn().apply(messageDatabaseObject);
                return switch (operator) {
                    case CONTAINS -> fieldValue.contains(value);
                    case STARTS_WITH -> fieldValue.startsWith(value);
                    case EQUALS -> fieldValue.equals(value);
                };
            };
            return objects.stream()
                    .filter(doesValueMatch)
                    .toList();
        });
    }

    @Override
    public Try<List<MessageDatabaseObject>> getObjects() {
        return Try.of(() -> objects);
    }

}
