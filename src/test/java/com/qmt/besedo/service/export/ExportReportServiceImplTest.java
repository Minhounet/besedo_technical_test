package com.qmt.besedo.service.export;

import com.qmt.besedo.exception.ReportException;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.service.export.csv.CSVReportConfiguration;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExportReportServiceImplTest {

    @Test
    void getCVSReport_WITH_error_dao_call_THROW_ReportException_with_get_all_objects_error() {
        var messageDao = mock(MessageDao.class);
        when(messageDao.getObjects()).thenReturn(Try.failure(new RuntimeException("any technical error")));
        var service = new ExportReportServiceImpl(null, messageDao);
        ReportException reportException = assertThrows(ReportException.class, () -> service.getCVSReport(null));
        assertEquals("Error when getting all objects", reportException.getMessage());
    }

    @Test
    void getCVSReport_WITH_error_when_writing_csv_THROW_ReportException_with_writing_exception() {
        var messageDao = mock(MessageDao.class);
        when(messageDao.getObjects()).thenReturn(Try.success(List.of()));
        var service = new ExportReportServiceImpl(null, messageDao); // null configuration make the csv writing fail :D
        ReportException reportException = assertThrows(ReportException.class, () -> service.getCVSReport(null));
        assertEquals("Error when writing csv report", reportException.getMessage());
    }

    @Test
    void getCVSReport_WITH_no_error_EXPECTED_csv_with_two_headers_only() {
        var expected = ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new ByteArrayResource("Id;Vowels count\r\n".getBytes()));

        var messageDao = mock(MessageDao.class);
        when(messageDao.getObjects()).thenReturn(Try.success(List.of()));
        var service = new ExportReportServiceImpl(new CSVReportConfiguration(), messageDao); // null configuration make the csv writing fail :D
        assertEquals(expected, service.getCVSReport(null));
    }
}