package com.qmt.besedo.service;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.repository.MessageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.qmt.besedo.response.Responses.buildResponseWithResultsFromExecution;

@RequiredArgsConstructor
@Service
public class GetMessageImpl implements GetMessage {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<Response> getMessages(String attribute, SearchOperator searchOperator, String value) {
        Function<Message, String> getAttribute = (mess) -> switch (attribute) {
            case "id" -> mess.id();
            case "email" -> mess.email();
            case "title" -> mess.title();
            case "body" -> mess.body();
            default -> throw new IllegalArgumentException("unknown field");
        };
        return buildResponseWithResultsFromExecution("query is successful",
                HttpStatus.OK,
                "Error when getting messages",
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageDao.getMessageByAttribute(value, searchOperator, getAttribute));

    }

}
