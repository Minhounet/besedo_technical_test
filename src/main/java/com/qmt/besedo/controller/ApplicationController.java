package com.qmt.besedo.controller;

import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.service.ExportReport;
import com.qmt.besedo.service.GetMessage;
import com.qmt.besedo.service.InjectMessage;
import com.qmt.besedo.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Entry point to rest call.
 */
@RequiredArgsConstructor
@RestController
public class ApplicationController {

    private final InjectMessage injectMessage;
    private final GetMessage getMessage;
    private final ExportReport exportReport;

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

    @GetMapping("reports/csv")
    public ResponseEntity<ByteArrayResource> getCSVReport() {
        return exportReport.getCVSReport();
    }

}
