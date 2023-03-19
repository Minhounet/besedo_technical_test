package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.MessageAttributeNamesMapping;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.utility.Strings;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.qmt.besedo.model.message.MessageAttributeNamesMapping.valueOf;
import static com.qmt.besedo.response.Responses.buildResponseWithResultsFromExecution;

@RequiredArgsConstructor
@Service
public class GetMessageImpl implements GetMessage {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<Response> getMessages(String filterAttributeName,
                                                SearchOperator searchOperator,
                                                String filterValue) {
        if (Strings.GET_SIZE.applyAsInt(filterAttributeName) == 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("attribute name is not valid", List.of("attribute name cannot be empty")));
        } else {
            Try<MessageAttributeNamesMapping> filterAttributeNameAttempt = Try.of(() -> valueOf(filterAttributeName.toUpperCase()));
            if (filterAttributeNameAttempt.isSuccess()) {
                return buildResponseWithResultsFromExecution("query is successful",
                        HttpStatus.OK,
                        "Error when getting messages",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageDao.getMessageByAttribute(filterValue,
                                searchOperator,
                                filterAttributeNameAttempt.get().getGetMatchingAttribute()));
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("attribute name is not valid", List.of("attribute " + filterAttributeName + " is not valid")));
            }
        }
    }

}
