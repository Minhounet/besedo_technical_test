package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import io.vavr.control.Try;

import java.util.List;

public interface MessageDao {

    Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject message);

    Try<List<MessageDatabaseObject>> getMessageByAttribute(MessageAttributeName attributeName,SearchOperator operator, String value);

    Try<List<MessageDatabaseObject>> getObjects();



}
