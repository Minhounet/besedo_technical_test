package com.qmt.besedo.model.message;

import lombok.Getter;

import java.util.function.Function;

/**
 * Mapping between the {@link Message} fields, {@link MessageDatabaseObject} fields and {@link String} attribute name values
 */
public enum MessageAttributeName {

    ID("id", Message::id, MessageDatabaseObject::getId),
    EMAIL("email", Message::email, MessageDatabaseObject::getEmail),
    TITLE("title", Message::title, MessageDatabaseObject::getTitle),
    BODY("body", Message::body, MessageDatabaseObject::getBody);

    @Getter
    private final String name;

    @Getter
    private final Function<Message, String> messageAttributeFn;

    @Getter
    private final Function<MessageDatabaseObject, String> messageDatabaseObjectAttributeFn;

    MessageAttributeName(String name,
                         Function<Message, String> messageAttributeFn,
                         Function<MessageDatabaseObject, String> messageDatabaseObjectAttributeFn) {
        this.name = name;
        this.messageAttributeFn = messageAttributeFn;
        this.messageDatabaseObjectAttributeFn = messageDatabaseObjectAttributeFn;
    }

}
