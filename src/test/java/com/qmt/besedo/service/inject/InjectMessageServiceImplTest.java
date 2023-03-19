package com.qmt.besedo.service.inject;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InjectMessageServiceImplTest {

    @Test
    void inject_WITH_invalid_Message_EXPECTED_invalid_message_response() {
        var expected = ResponseEntity
                .badRequest()
                .body(new ErrorResponse("Message is invalid", List.of("Message cannot be null")));
        var service = new InjectMessageServiceImpl(mock(MessageDao.class));
        assertEquals(expected, service.inject(null));
    }

    @Test
    void inject_WITH_successful_dao_call_EXPECTED_response_with_creation_confirmation_message() {
        var expected = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse("Message created successfully"));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.injectMessage(any())).thenReturn(Try.success(new Message("id", "besedo@gmail.com", "besedo", null)));
        var service = new InjectMessageServiceImpl(messageDao);
        assertEquals(expected, service.inject(new Message("id", "besedo@gmail.com", "besedo", null)));
    }
    @Test
    void inject_WITH_error_dao_call_EXPECTED_response_with_error_message() {
        var expected = ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Error when injecting message, please contact your administrator", List.of()));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.injectMessage(any())).thenReturn(Try.failure(new RuntimeException("Error because this is life")));
        var service = new InjectMessageServiceImpl(messageDao);
        assertEquals(expected, service.inject(new Message("id", "besedo@gmail.com", "besedo", null)));
    }

}