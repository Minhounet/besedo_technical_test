package com.qmt.besedo.service.export;

import com.qmt.besedo.exception.ReportException;
import com.qmt.besedo.model.message.Message;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Component
public class ExportReportServiceImpl implements ExportReportService {

    private final CSVReportConfiguration csvReportConfiguration;
    private final MessageDao messageDao;

    @Override
    public ResponseEntity<ByteArrayResource> getCVSReport() {
        Function<MessageDao, List<Message>> getAllMessages = dao ->  dao.getObjects()
                .getOrElseThrow(cause -> new ReportException("Error when getting all objects", cause)).stream()
                .toList();

        Function<List<Message>, List<Tuple2<String, Integer>>> enrichMessagesWithVowelsCount = messages ->
                messages.stream()
                        .map(message -> Tuple.of(message.id(), Strings.GET_VOWELS_COUNT.apply(message.body())))
                        .toList();

        Function<List<Tuple2<String, Integer>>, byte[]> writeEntriesToBytes = entries ->
                writeCSV(entries).getOrElseThrow(cause -> new ReportException("Error when writing csv report", cause));

        Function<byte[], ResponseEntity<ByteArrayResource>> buildResponse =  csvBytes -> ResponseEntity
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

    /**
     *
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
