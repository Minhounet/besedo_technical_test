package com.qmt.besedo.repository;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.repository.list.ObjectsListDao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectsListDaoTest {

    @Test
    void getObjects_EXPECTED_non_null_list() {
        assertNotNull(new ObjectsListDao().getObjects());
    }

    @Test
    void injectMessage_WITH_Message_EXPECTED_same_Message() {
        var entity = buildObject();
        assertEquals(entity, new ObjectsListDao().injectMessage(entity).get());
    }

    private MessageDatabaseObject buildObject() {
        return buildObject(null, null, null, null);
    }

    private MessageDatabaseObject buildObject(String id, String email, String title, String body) {
        var effectiveId = id == null ? "id" : id;
        var effectiveEmail = email == null ? "email" : id;
        var effectiveTitle = title == null ? "title" : id;
        var effectiveBody = body == null ? "body" : id;
        var entity = new MessageDatabaseObject();
        entity.setInternalId(1);
        entity.setId(effectiveId);
        entity.setEmail(effectiveEmail);
        entity.setTitle(effectiveTitle);
        entity.setBody(effectiveBody);
        return entity;
    }

    @Test
    void getMessageByAttribute_WITH_equals_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = buildObject("id", "email", "title", "body");
        var message1 = buildObject("notid", "email", "title", "body");
        var message2 = buildObject("id", "email2", "title", "body");

        ObjectsListDao database = new ObjectsListDao();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<MessageDatabaseObject> results = database.getMessageByAttribute(MessageAttributeName.ID, SearchOperator.EQUALS, "id").get();

        // Message is not comparable, use bold assert.
        assertEquals(2, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message2));
    }

    @Test
    void getMessageByAttribute_WITH_equals_operator_AND_non_matching_value_EXPECTED_all_matching_values() {
        var message0 = buildObject("id", "email", "title", "body");
        var message1 = buildObject("notid", "email", "title", "body");
        var message2 = buildObject("id", "email2", "title", "body");

        ObjectsListDao database = new ObjectsListDao();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        assertEquals(List.of(), database.getMessageByAttribute(MessageAttributeName.ID, SearchOperator.EQUALS, "besedo besedo mucho").get());
    }

    @Test
    void getMessageByAttribute_WITH_starts_with_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = buildObject("id", "email", "title", "body");
        var message1 = buildObject("notid", "email", "title", "body");
        var message2 = buildObject("id", "email2", "title", "body");

        ObjectsListDao database = new ObjectsListDao();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<MessageDatabaseObject> results = database.getMessageByAttribute(MessageAttributeName.ID, SearchOperator.STARTS_WITH, "id").get();

        // Message is not comparable, use bold assert.
        assertEquals(2, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message2));
    }

    @Test
    void getMessageByAttribute_WITH_contains_operator_AND_matching_value_EXPECTED_all_matching_values() {
        var message0 = buildObject("thisis the id", "email", "title", "body");
        var message1 = buildObject("oh id ohoho", "email", "title", "body");
        var message2 = buildObject("idea", "email2", "title", "body");

        ObjectsListDao database = new ObjectsListDao();
        database.injectMessage(message0);
        database.injectMessage(message1);
        database.injectMessage(message2);

        List<MessageDatabaseObject> results = database.getMessageByAttribute(MessageAttributeName.ID, SearchOperator.CONTAINS, "id").get();

        // Message is not comparable, use bold assert.
        assertEquals(3, results.size());
        assertTrue(results.contains(message0));
        assertTrue(results.contains(message1));
        assertTrue(results.contains(message2));
    }
}