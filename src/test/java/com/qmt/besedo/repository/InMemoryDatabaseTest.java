package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.operator.SearchOperator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest {

    @Test
    void getObjects_EXPECTED_non_null_list() {
        assertNotNull(new InMemoryDatabase().getObjects());
    }

    @Test
    void injectMessage_WITH_Message_EXPECTED_same_Message() {
        var message = new Message("id", "email", "title", "body");
        assertEquals(message, new InMemoryDatabase().injectMessage(message).get());
    }


    @Test
    void getMessageByAttribute_WITH_equals_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = new Message("id", "email", "title", "body");
        var message1 = new Message("notid", "email", "title", "body");
        var message2 = new Message("id", "email2", "title", "body");

        InMemoryDatabase database = new InMemoryDatabase();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<Message> results = database.getMessageByAttribute("id", SearchOperator.EQUALS, Message::id).get();

        // Message is not comparable, use bold assert.
        assertEquals(2, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message2));
    }

    @Test
    void getMessageByAttribute_WITH_equals_operator_AND_non_matching_value_EXPECTED_all_matching_values() {
        var message0 = new Message("id", "email", "title", "body");
        var message1 = new Message("notid", "email", "title", "body");
        var message2 = new Message("id", "email2", "title", "body");

        InMemoryDatabase database = new InMemoryDatabase();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        assertEquals(List.of(), database.getMessageByAttribute("besedo", SearchOperator.EQUALS, Message::id).get());
    }

    @Test
    void getMessageByAttribute_WITH_starts_with_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = new Message("ideal", "email", "title", "body");
        var message1 = new Message("notid", "email", "title", "body");
        var message2 = new Message("idea", "email2", "title", "body");

        InMemoryDatabase database = new InMemoryDatabase();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<Message> results = database.getMessageByAttribute("id", SearchOperator.STARTS_WITH, Message::id).get();

        // Message is not comparable, use bold assert.
        assertEquals(2, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message2));
    }

    @Test
    void getMessageByAttribute_WITH_contains_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = new Message("thisis the id", "email", "title", "body");
        var message1 = new Message("oh id ohoho", "email", "title", "body");
        var message2 = new Message("idea", "email2", "title", "body");

        InMemoryDatabase database = new InMemoryDatabase();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<Message> results = database.getMessageByAttribute("id", SearchOperator.CONTAINS, Message::id).get();

        // Message is not comparable, use bold assert.
        assertEquals(3, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message1));
        assertTrue(results.contains(message2));
    }
}