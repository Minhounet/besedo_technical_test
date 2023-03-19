package com.qmt.besedo.service.export;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface ExportReport {

    ResponseEntity<ByteArrayResource> getCVSReport();
}
