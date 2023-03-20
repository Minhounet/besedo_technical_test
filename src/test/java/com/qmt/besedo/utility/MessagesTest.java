package com.qmt.besedo.utility;

import com.qmt.besedo.model.message.Message;
import io.vavr.collection.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessagesTest {

    @Test
    void requireValidMessage_WITH_null_Message_EXPECTED_error_message() {
        assertEquals(invalid(List.of("Message cannot be null")),Messages.REQUIRE_VALID_MESSAGE.apply(null));
    }

    @Test
    void requireValidMessage_WITH_null_id_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, null, null, null))
                .getError()
                .contains("id size is not valid, it should be between 1 and 100 (current size: 0)"));

    }

    @Test
    void requireValidMessage_WITH_blank_id_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message("    ", null, null, null))
                .getError()
                .contains("id size is not valid, it should be between 1 and 100 (current size: 0)"));

    }

    @Test
    void requireValidMessage_WITH_101_sized_id_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891",
                        null,
                        null,
                        null))
                .getError()
                .contains("id size is not valid, it should be between 1 and 100 (current size: 101)"));

    }

    @Test
    void requireValidMessage_WITH_null_email_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, null, null, null))
                .getError()
                .contains("Mail is mandatory and cannot be blank"));

    }

    @Test
    void requireValidMessage_WITH_blank_email_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "    ", null, null))
                .getError()
                .contains("Mail is mandatory and cannot be blank"));

    }

    @Test
    void requireValidMessage_WITH_invalid_email_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "this is not an email", null, null))
                .getError()
                .contains("Mail is not valid"));

    }

    @Test
    void requireValidMessage_WITH_null_title_AND_null_body_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "this is not an email", null, null))
                .getError()
                .contains("title and body are not defined, one of them must be defined at least"));

    }

    @Test
    void requireValidMessage_WITH_blank_title_AND_blank_body_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "this is not an email", "     ", "    "))
                .getError()
                .contains("title and body are not defined, one of them must be defined at least"));

    }

    @Test
    void requireValidMessage_WITH_title_exceeding_size_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "this is not an email", StringUtils.repeat("a",500), "    "))
                .getError()
                .contains("id size is not valid, it should not exceed 300"));

    }

    @Test
    void requireValidMessage_WITH_body_exceeding_size_EXPECTED_error_message() {
        assertTrue(Messages.REQUIRE_VALID_MESSAGE
                .apply(new Message(null, "this is not an email", "", StringUtils.repeat("a",10010)))
                .getError()
                .contains("id size is not valid, it should not exceed 10000"));

    }


    @Test
    void requireValidMessage_WITH_valid_Message_EXPECTED_Message() {
        var expected = valid(new Message("besedoId", "anyone@gmail.com", "test me", "I wanna feel your body"));
        assertEquals(expected, Messages.REQUIRE_VALID_MESSAGE.apply(new Message("besedoId", "anyone@gmail.com", "test me", "I wanna feel your body")));

    }



}