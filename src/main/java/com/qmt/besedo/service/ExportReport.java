package com.qmt.besedo.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface ExportReport {

    ResponseEntity<ByteArrayResource> getCVSReport();
}
