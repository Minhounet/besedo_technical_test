package com.qmt.besedo.model.message;

/**
 * Represents a message to be returned to the end user.
 * @param id id of the message
 * @param email email of the message
 * @param title title of the message
 * @param body body of the message
 */
public record Message(String id, String email, String title, String body) {

    public MessageDatabaseObject toDatabaseObject() {
        MessageDatabaseObject databaseObject = new MessageDatabaseObject();
        databaseObject.setId(this.id());
        databaseObject.setEmail(this.email());
        databaseObject.setBody(this.body());
        databaseObject.setTitle(this.title());
        return databaseObject;
    }
}
