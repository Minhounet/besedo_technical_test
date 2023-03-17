package com.qmt.besedo.controller;

import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.service.GetMessage;
import com.qmt.besedo.service.InjectMessage;
import com.qmt.besedo.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

import static io.vavr.API.TODO;

/**
 * Entry point to rest call.
 */
@RequiredArgsConstructor
@RestController
public class ApplicationController {

    private final InjectMessage injectMessage;
    private final GetMessage getMessage;

    @PostMapping("mails")
    public ResponseEntity<Response> injectMessage(@RequestBody Message message) {
        return injectMessage.inject(message);
    }

    @GetMapping("mails")
    public ResponseEntity<Response> getMails(@RequestParam String attribute,
                                             @RequestParam(required = false) SearchOperator operator,
                                             @RequestParam String value) {
        return getMessage.getMessages(attribute, operator, value);
    }

    @GetMapping("report")
    public ResponseEntity<ByteArrayOutputStream> getCSVReport() {
        return TODO("Return csv report");
    }

}
