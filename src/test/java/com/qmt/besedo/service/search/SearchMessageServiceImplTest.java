package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.SuccessResponseWithResults;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchMessageServiceImplTest {


    @Test
    void getMessages_WITH_invalid_attribute_name_EXPECTED_invalid_attribute_error() {
        var expected = ResponseEntity
                .badRequest()
                .body(new ErrorResponse("attribute name is not valid", List.of("attribute besedo is not valid")));

        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(mock(MessageDao.class));
        assertEquals(expected, searchMessageService.getMessages("besedo", null, null));
    }

    @Test
    void getMessages_WITH_null_attribute_name_EXPECTED_empty_attribute_error() {
        var expected = ResponseEntity
                .badRequest()
                .body(new ErrorResponse("attribute name is not valid", List.of("attribute name cannot be empty")));

        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(mock(MessageDao.class));
        assertEquals(expected, searchMessageService.getMessages(null, null, null));
    }

    @Test
    void getMessages_WITH_success_dao_call_EXPECTED_success_response() {
        var expected = ResponseEntity
                .ok()
                .body(new SuccessResponseWithResults<Message>("query is successful", List.of()));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.getMessageByAttribute(anyString(), any(), any())).thenReturn(Try.of(List::of));
        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(messageDao);
        assertEquals(expected, searchMessageService.getMessages("id", SearchOperator.EQUALS, "anything"));
    }

    @Test
    void getMessages_WITH_error_dao_call_EXPECTED_error_response() {
        var expected = ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Error when getting messages, please contact your administrator", List.of()));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.getMessageByAttribute(anyString(), any(), any())).thenReturn(Try.failure(new RuntimeException("besedo error")));
        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(messageDao);
        assertEquals(expected, searchMessageService.getMessages("id", SearchOperator.EQUALS, "anything"));
    }


}