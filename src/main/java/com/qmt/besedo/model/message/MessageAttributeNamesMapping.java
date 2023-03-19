package com.qmt.besedo.model.message;

import lombok.Getter;

import java.util.function.Function;

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
