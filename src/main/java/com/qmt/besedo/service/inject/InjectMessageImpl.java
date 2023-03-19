package com.qmt.besedo.service.inject;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.repository.MessageDao;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.qmt.besedo.utility.Messages.requireValidMessage;
import static com.qmt.besedo.response.Responses.buildResponseFromExecution;

@RequiredArgsConstructor
@Service
public class InjectMessageImpl implements InjectMessage {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<Response> inject(Message message) {
        Validation<Seq<String>, Message> mailValidation = requireValidMessage(message);
        if (mailValidation.isInvalid()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Message is invalid", mailValidation.getError().toJavaList()));
        } else {
            return buildResponseFromExecution("Message created successfully",
                    HttpStatus.CREATED,
                    "Error when injecting message",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageDao.injectMessage(message));
        }

    }
}
