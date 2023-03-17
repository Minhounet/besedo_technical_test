package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.operator.SearchOperator;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryDatabase implements MessageDao {

    private final List<Message> objects = new CopyOnWriteArrayList<>();

    @Override
    public Try<Message> injectMessage(Message message) {
        return Try.of(() -> {
            objects.add(message);
            return message;
        });
    }

    @Override
    public Try<List<Message>> getMessageByAttribute(String value, SearchOperator operator, Function<Message, String> getFieldValue) {
        return Try.of(() -> {
            Predicate<Message> doesValueMatch = message -> {
                String fieldValue = getFieldValue.apply(message);
                return switch (operator) {
                    case CONTAINS -> fieldValue.contains(value);
                    case STARTS_WITH -> fieldValue.startsWith(value);
                    case EQUALS -> fieldValue.equals(value);
                };
            };
            return objects.stream()
                    .filter(doesValueMatch)
                    .collect(toList());
        });
    }

}
