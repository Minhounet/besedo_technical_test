package com.qmt.besedo.service.export;

import com.qmt.besedo.model.response.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Service dedicated to {@link com.qmt.besedo.model.message.Message} report exports.
 */
public interface ExportReportService {

    /**
     * Return the CSV report with two columns ("Id", "Vowels count").
     *
     * @return the {@link com.qmt.besedo.model.message.Message} as a CSV file.
     */
    ResponseEntity<ByteArrayResource> getCVSReport(String requestId);

    /**
     * @return the request id to get the csv report when it is available.
     */
    ResponseEntity<Response> requestCSVReport();

    /**
     * Test if requestId exists.
     * @param requestId the request id.
     * @return Response
     */
    ResponseEntity<Response> getRequest(UUID requestId);
}
