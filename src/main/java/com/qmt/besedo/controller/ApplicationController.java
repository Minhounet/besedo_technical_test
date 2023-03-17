package com.qmt.besedo.controller;

import com.qmt.besedo.business.InjectMessage;
import com.qmt.besedo.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

import static io.vavr.API.TODO;

/**
 * Entry point to rest call.
 */
@RequiredArgsConstructor
@RestController
public class ApplicationController {


    private final InjectMessage injectMessage;

    @PostMapping("mails")
    public ResponseEntity<String> createMail(@RequestBody Message message) {
        return injectMessage.inject(message);
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
