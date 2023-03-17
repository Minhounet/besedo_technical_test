package com.qmt.besedo.controller;

import com.qmt.besedo.model.Message;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

import static com.qmt.besedo.model.MessageValidation.*;
import static io.vavr.API.TODO;

/**
 * Entry point to rest call.
 */
@RestController
public class ApplicationController {

    @PostMapping("mails")
    public ResponseEntity<String> createMail(@RequestBody Message message) {
        Validation<Seq<String>, Message> mailValidation = requireValidMMail(message);
        if (mailValidation.isValid()) {
            return ResponseEntity.ok().build();
        } else {
            String mergedErrors = mailValidation
                    .getError()
                    .reduce((a, b) -> a + "\n" + b);
            return ResponseEntity
                    .badRequest()
                    .body(mergedErrors);
        }
    }

    @GetMapping("mails")
    public ResponseEntity<String> getMails() {
        return TODO("get mails using possible filters (like operator ?");
    }

    @GetMapping("report")
    public ResponseEntity<ByteArrayOutputStream> getCSVReport() {
        return TODO("Return csv report");
    }

}
