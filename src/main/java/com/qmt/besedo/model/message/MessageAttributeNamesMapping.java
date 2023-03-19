package com.qmt.besedo.model.message;

import com.qmt.besedo.model.operator.SearchOperator;
import lombok.Getter;

import java.util.function.Function;

/**
 * Mapping between the {@link Message} fields and the "string" version.
 * @see com.qmt.besedo.service.search.SearchMessageService#getMessages(String, SearchOperator, String)
 */
public enum MessageAttributeNamesMapping {

    ID("id", Message::id), EMAIL("email", Message::email), TITLE("title", Message::title), BODY("body", Message::body);

    @Getter
    private final String name;

    @Getter
    private final Function<Message, String> getMatchingAttribute;


    MessageAttributeNamesMapping(String name, Function<Message, String> getMatchingAttribute) {
        this.name = name;
        this.getMatchingAttribute = getMatchingAttribute;
    }

}
