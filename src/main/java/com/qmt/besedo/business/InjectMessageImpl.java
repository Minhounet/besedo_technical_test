package com.qmt.besedo.business;

import com.qmt.besedo.model.Message;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.qmt.besedo.model.MessageValidation.requireValidMessage;
import static io.vavr.API.TODO;

@Service
public class InjectMessageImpl implements InjectMessage {
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
            return TODO("inject into base");
        }

    }
}
