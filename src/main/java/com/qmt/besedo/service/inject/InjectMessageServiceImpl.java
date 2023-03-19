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

import static com.qmt.besedo.response.Responses.buildNoResultResponseFromExecution;
import static com.qmt.besedo.utility.Messages.REQUIRE_VALID_MESSAGE;

@RequiredArgsConstructor
@Service
public class InjectMessageServiceImpl implements InjectMessageService {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<Response> inject(Message message) {
        Validation<Seq<String>, Message> mailValidation = REQUIRE_VALID_MESSAGE.apply(message);
        if (mailValidation.isInvalid()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Message is invalid", mailValidation.getError().toJavaList()));
        } else {
            return buildNoResultResponseFromExecution("Message created successfully",
                    HttpStatus.CREATED,
                    "Error when injecting message, please contact your administrator",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageDao.injectMessage(message));
        }

    }
}
