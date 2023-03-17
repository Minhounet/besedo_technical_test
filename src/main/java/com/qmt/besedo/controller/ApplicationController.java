package com.qmt.besedo.controller;

import com.qmt.besedo.model.Mail;
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
@RestController
public class ApplicationController {

    @PostMapping("mails")
    public ResponseEntity<String> createMail(@RequestBody Mail mail) {
        return TODO("Inject json message");
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
