package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.service.search.SearchResults;
import io.vavr.control.Try;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageDao {

    Try<MessageDatabaseObject> injectMessage(MessageDatabaseObject message);

    Try<SearchResults> getMessageByAttribute(MessageAttributeName attributeName, SearchOperator operator, String value, Pageable pageable);

    Try<List<MessageDatabaseObject>> getObjects();



}
