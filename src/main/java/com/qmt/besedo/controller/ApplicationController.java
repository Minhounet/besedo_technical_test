package com.qmt.besedo.controller;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.service.export.ExportReportService;
import com.qmt.besedo.service.search.SearchMessageService;
import com.qmt.besedo.service.inject.InjectMessageService;
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
@RequestMapping("/api/v1")
public class ApplicationController {

    private final InjectMessageService injectMessageService;
    private final SearchMessageService searchMessageService;
    private final ExportReportService exportReportService;

    @PostMapping("/messages")
    public ResponseEntity<Response> injectMessage(@RequestBody Message message) {
        return injectMessageService.inject(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<Response> getMails(@RequestParam MessageAttributeName attribute,
                                             @RequestParam SearchOperator operator,
                                             @RequestParam String value) {
        return searchMessageService.getMessages(attribute, operator, value);
    }

    @GetMapping("/reports/csv")
    public ResponseEntity<ByteArrayResource> getCSVReport() {
        return exportReportService.getCVSReport();
    }

}
