package com.qmt.besedo.model.message;

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
