package com.qmt.besedo.model.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MessageDatabaseObject {

    @Id
    private long internalId;

    private String id;
    private String email;
    private String title;
    private String body;

    public Message toMessage() {
        return new Message(this.getId(),
                this.getEmail(),
                this.getTitle(),
                this.getBody());
    }

}
