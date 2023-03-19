package com.qmt.besedo.service.export;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

/**
 * Service dedicated to {@link com.qmt.besedo.model.message.Message} report exports.
 */
public interface ExportReportService {

    /**
     * Return the CSV report with two columns ("Id", "Vowels count").
     *
     * @return the {@link com.qmt.besedo.model.message.Message} as a CSV file.
     */
    ResponseEntity<ByteArrayResource> getCVSReport();
}
