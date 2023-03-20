package com.qmt.besedo.model.message;

public record Message(String id, String email, String title, String body) {

    public MessageDatabaseObject toDatabaseObject() {
        MessageDatabaseObject databaseObject = new MessageDatabaseObject();
        databaseObject.setBody(this.body());
        databaseObject.setId(this.id());
        databaseObject.setBody(this.title());
        databaseObject.setBody(this.body());
        return databaseObject;
    }
}
