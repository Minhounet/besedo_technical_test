package com.qmt.besedo.controller;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.service.export.ExportReportService;
import com.qmt.besedo.service.inject.InjectMessageService;
import com.qmt.besedo.service.search.SearchMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    /**
     *
     * @param message the {@link Message} to insert to the database
     * @return Response with a confirmation message
     */
    @PostMapping("/messages")
    public ResponseEntity<Response> injectMessage(@RequestBody Message message) {
        return injectMessageService.inject(message);
    }

    /**
     *
     * @param attribute the {@link MessageAttributeName} involved to filter the query
     * @param operator the {@link SearchOperator}
     * @param value the filter value
     * @param pageIndex the index of page to return, starts from 0. Default value is 0.
     * @param pageSize the  nb of elements to return per page. Default value is 100
     * @return Response containing the list of results using a pagination
     */
    @GetMapping("/messages")
    public ResponseEntity<Response> getMessages(@RequestParam MessageAttributeName attribute,
                                                @RequestParam SearchOperator operator,
                                                @RequestParam String value,
                                                @RequestParam(defaultValue = "0") int pageIndex,
                                                @RequestParam(defaultValue = "100") int pageSize) {
        var pageRequest = PageRequest.of(pageIndex, pageSize);
        return searchMessageService.getMessages(attribute, operator, value, pageRequest);
    }

    /**
     * @return generated request id to get the report when it is done. If request has already been done, inform end user.
     */
    @PostMapping("/reports/csv/request")
    public ResponseEntity<Response> launchReportGeneration() {
        return exportReportService.launchReportGeneration();
    }

    /**
     *
     * @param requestId the request id to get the report
     * @return Response indicating is csv is being generated or not. If request is not valid, return an error.
     */
    @GetMapping("/reports/csv/request")
    public ResponseEntity<Response> getRequestStatus(@RequestParam String requestId) {
        return exportReportService.getRequestStatus(UUID.fromString(requestId));
    }

    /**
     * @return the CSV report when it is ready, null otherwise
     */
    @GetMapping("/reports/csv/content")
    public ResponseEntity<ByteArrayResource> downloadReport(@RequestParam String requestId) {
        return exportReportService.getReport(requestId);
    }

}
