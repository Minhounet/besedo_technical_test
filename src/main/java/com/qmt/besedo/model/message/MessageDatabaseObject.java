package com.qmt.besedo.model.message;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * The message in a true database.
 */
@Entity
@Data
public class MessageDatabaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long internalId;

    @Length(min = 1, max = 100)
    private String id;

    @Column(nullable = false)
    private String email;

    @Length(max = 300)
    private String title;

    @Length(max = 10000)
    private String body;

    public Message toMessage() {
        return new Message(this.getId(),
                this.getEmail(),
                this.getTitle(),
                this.getBody());
    }

}
