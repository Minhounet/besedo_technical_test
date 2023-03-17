package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.SearchOperator;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Repository
public class ReadFastMemoryDatabase implements MessageDao {

    private final List<Message> objects = new CopyOnWriteArrayList<>();

    @Override
    public Try<Message> injectMessage(Message message) {
        return Try.of(() -> {
            objects.add(message);
            return message;
        });
    }

}
