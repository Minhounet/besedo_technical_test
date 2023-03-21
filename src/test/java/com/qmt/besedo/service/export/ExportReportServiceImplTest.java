package com.qmt.besedo.service.export;

import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.ReportResponse;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.service.export.csv.CSVReportConfiguration;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ExportReportServiceImplTest {


    @Test
    void getReport_WITH_invalid_requestId_EXPECTED_null_body() {
        var expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        var service = new ExportReportServiceImpl(null, null);
        assertEquals(expected, service.getReport("43f4e775-b37e-4fca-8616-341c05e3b7b2"));
    }

    @Test
    void getReport_WITH_non_available_content_EXPECTED_null_body() {
        var expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        var service = spy(new ExportReportServiceImpl(null, null));
        doAnswer(a -> {
            service.available = false;
            return null;
        }).when(service).launchReportGeneration();
        service.launchReportGeneration();
        assertEquals(expected, service.getReport("43f4e775-b37e-4fca-8616-341c05e3b7b2"));
    }

    @Test
    void getReport_WITH_available_content_EXPECTED_content() {
        String besedoString = "hi";
        var expected = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new ByteArrayResource(besedoString.getBytes()));

        var service = spy(new ExportReportServiceImpl(null, null));
        String requestId = "43f4e775-b37e-4fca-8616-341c05e3b7b2";
        doAnswer(a -> {
            service.csvRequestId = UUID.fromString(requestId);
            service.available = true;
            service.csvContent = new ByteArrayResource(besedoString.getBytes());
            return null;
        }).when(service).launchReportGeneration();
        service.launchReportGeneration();
        assertEquals(expected, service.getReport(requestId));
    }

    @Test
    void getRequestStatus_WITH_non_valid_request_EXPECTED_request_not_found_response() {
        var expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Request not found", List.of()));
        var service = new ExportReportServiceImpl(null, null);
        assertEquals(expected, service.getRequestStatus(UUID.randomUUID()));
    }

    @Test
    void getRequestStatus_WITH_valid_request_EXPECTED_request_found_response() {
        var anyId = UUID.randomUUID();
        var expected = ResponseEntity.ok().body(new ReportResponse(anyId, true));
        var service = spy(new ExportReportServiceImpl(null, null));
        doAnswer(a -> {
            service.available = true;
            service.csvRequestId = anyId;
            return null;
        }).when(service).launchReportGeneration();
        service.launchReportGeneration();
        assertEquals(expected, service.getRequestStatus(anyId));
    }

    @Test
    void launchReportGeneration_WITH_no_request_in_process_EXPECTED_new_request_id() {
        var messageDao = mock(MessageDao.class);
        when(messageDao.getObjects()).thenReturn(Try.success(List.of()));
        var service = new ExportReportServiceImpl(null, messageDao);
        var actual = service.launchReportGeneration();
        assertNotNull(service.csvRequestId);

        var expected = ResponseEntity
                .accepted()
                .body(new SuccessResponse(service.csvRequestId.toString()));
        assertEquals(expected, actual);
    }

    @Test
    void launchReportGeneration_WITH_request_in_process_EXPECTED__request_in_process_response() {
        var messageDao = mock(MessageDao.class);
        when(messageDao.getObjects()).thenReturn(Try.success(List.of()));
        var id = UUID.randomUUID();
        var expected = ResponseEntity
                .ok()
                .body(new SuccessResponse(id + " is processing, must wait for it termination"));
        when(messageDao.getObjects()).thenReturn(Try.success(List.of()));
        var service = new ExportReportServiceImpl(null, messageDao);
        service.csvRequestId = id;
        var actual = service.launchReportGeneration();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_EXPECTED_csv_content() {
        var expected = new ByteArrayResource("Id;Vowels count\r\nbesedo;3\r\n".getBytes());
        var messageDao = mock(MessageDao.class);
        var object = new MessageDatabaseObject();
        object.setId("besedo");
        object.setBody("besedo y");
        when(messageDao.getObjects()).thenReturn(Try.success(List.of(object)));
        var service = new ExportReportServiceImpl(new CSVReportConfiguration(), messageDao);
        service.generateReport();
        assertEquals(expected, service.csvContent);
    }

}