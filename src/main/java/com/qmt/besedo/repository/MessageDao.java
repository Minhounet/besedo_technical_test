package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.operator.SearchOperator;
import io.vavr.control.Try;

import java.util.List;
import java.util.function.Function;

public interface MessageDao {

    Try<Message> injectMessage(Message message);

    Try<List<Message>> getMessageByAttribute(String value, SearchOperator operator, Function<Message, String> getFieldValue);

    Try<List<Message>> getObjects();
}
