package com.qmt.besedo.service;

import com.qmt.besedo.model.Message;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.response.Responses;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.qmt.besedo.model.MessageValidation.requireValidMessage;

@RequiredArgsConstructor
@Service
public class InjectMessageImpl implements InjectMessage {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<String> inject(Message message) {
        Validation<Seq<String>, Message> mailValidation = requireValidMessage(message);
        if (mailValidation.isInvalid()) {
            String mergedErrors = mailValidation
                    .getError()
                    .reduce((a, b) -> a + "\n" + b);
            return ResponseEntity
                    .badRequest()
                    .body(mergedErrors);
        } else {
            return Responses.executeAndBuildResponse("Error when injecting message", messageDao.injectMessage(message));
        }

    }
}
