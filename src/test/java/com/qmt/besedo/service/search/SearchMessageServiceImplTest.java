package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.SuccessResponseWithResults;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchMessageServiceImplTest {

    @Test
    void getMessages_WITH_success_dao_call_EXPECTED_success_response() {
        var expected = ResponseEntity
                .ok()
                .header("x-total-count","1")
                .header("x-total-pages","1")
                .header("x-current-page","1")
                .body(new SuccessResponseWithResults<Message>("query is successful", List.of()));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.getMessageByAttribute(any(), any(), any(), any())).thenReturn(Try.success(new SearchResults(List.of(), 1, 1, 1)));

        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(messageDao);
        assertEquals(expected, searchMessageService.getMessages(MessageAttributeName.ID, SearchOperator.EQUALS, "anything", Pageable.unpaged()));
    }

    @Test
    void getMessages_WITH_error_dao_call_EXPECTED_error_response() {
        var expected = ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Error when getting messages, please contact your administrator", List.of()));
        MessageDao messageDao = mock(MessageDao.class);
        when(messageDao.getMessageByAttribute(any(), any(), any(), any())).thenReturn(Try.failure(new RuntimeException("besedo error")));
        SearchMessageServiceImpl searchMessageService = new SearchMessageServiceImpl(messageDao);
        assertEquals(expected, searchMessageService.getMessages(MessageAttributeName.ID, SearchOperator.EQUALS, "anything", Pageable.unpaged()));
    }


}