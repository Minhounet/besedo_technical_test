package com.qmt.besedo.service.export;

import com.qmt.besedo.exception.ReportException;
import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.message.MessageDatabaseObject;
import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.ReportResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.service.export.csv.CSVReportConfiguration;
import com.qmt.besedo.service.export.csv.CSVPrinterWithOutput;
import com.qmt.besedo.utility.Strings;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Component
public class ExportReportServiceImpl implements ExportReportService {

    private final CSVReportConfiguration csvReportConfiguration;
    private final MessageDao messageDao;

    private UUID csvRequestId = null;
    private ByteArrayResource csvContent = null;
    private boolean inProcess = false;

    @Override
    public ResponseEntity<ByteArrayResource> getCVSReport(String requestId) {
        synchronized (this) {
            if (UUID.fromString(requestId).equals(csvRequestId)) {
                csvRequestId = null;
                return ResponseEntity.ok().body(csvContent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }

    public ResponseEntity<ByteArrayResource> temp() {
        Function<MessageDao, List<Message>> getAllMessages = dao -> dao.getObjects()
                .getOrElseThrow(cause -> new ReportException("Error when getting all objects", cause)).stream()
                .map(MessageDatabaseObject::toMessage)
                .toList();

        Function<List<Message>, List<Tuple2<String, Integer>>> enrichMessagesWithVowelsCount = messages ->
                messages.stream()
                        .map(message -> Tuple.of(message.id(), Strings.GET_VOWELS_COUNT.apply(message.body())))
                        .toList();

        Function<List<Tuple2<String, Integer>>, byte[]> writeEntriesToBytes = entries ->
                writeCSV(entries).getOrElseThrow(cause -> new ReportException("Error when writing csv report", cause));

        Function<byte[], ResponseEntity<ByteArrayResource>> buildResponse = csvBytes -> ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new ByteArrayResource(csvBytes));

        return getAllMessages
                .andThen(enrichMessagesWithVowelsCount)
                .andThen(writeEntriesToBytes)
                .andThen(buildResponse)
                .apply(messageDao);
    }

    @Override
    public ResponseEntity<Response> requestCSVReport() {
        synchronized (this) {
            if (null == csvRequestId) {
                csvRequestId = UUID.randomUUID();
                return ResponseEntity
                        .accepted()
                        .body(new SuccessResponse(csvRequestId.toString()));
            } else {
                return ResponseEntity
                        .ok()
                        .body(new SuccessResponse(csvRequestId + " is processing, must wait for it termination"));
            }
        }
    }

    @Override
    public ResponseEntity<Response> getRequest(UUID requestId) {
        synchronized (this) {
            if (requestId.equals(csvRequestId)) {
                return ResponseEntity.ok().body(new ReportResponse(csvRequestId, inProcess));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Request not found", List.of()));
            }
        }
    }

    /**
     * @param entries List of {@link com.qmt.besedo.model.message.Message} coupled with its vowels count
     * @return CSV as array of bytes.
     */
    private Try<byte[]> writeCSV(List<Tuple2<String, Integer>> entries) {
        return Try.withResources(() -> new CSVPrinterWithOutput(csvReportConfiguration)).of(printer -> {
            entries.forEach(entry -> {
                try {
                    printer.getCsvPrinter().printRecord(entry._1, entry._2);
                } catch (IOException e) {
                    throw new ReportException("Error writing value " + entry._1 + ", " + entry._2, e);
                }
            });
            printer.getCsvPrinter().flush();
            return printer.getOutput().toByteArray();
        });
    }

}
