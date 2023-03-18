package com.qmt.besedo.service;

import com.qmt.besedo.csv.CSVWithOutput;
import com.qmt.besedo.repository.MessageDao;
import com.qmt.besedo.string.Strings;
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
public class ExportReportImpl implements ExportReport {

    private final MessageDao messageDao;

    @Override
    public ResponseEntity<ByteArrayResource> getCVSReport() {
        List<Tuple2<String, Integer>> entries = messageDao.getObjects().stream()
                .map(message -> Tuple.of(message.id(), Strings.GET_VOWELS_COUNT.apply(message.body())))
                .toList();

        return writeCSV(entries)
                .onFailure(cause -> log.error("Error when writing csv report", cause))
                .fold(ignored -> ResponseEntity.internalServerError().body(null),
                        buildResponseWithCSV());
    }

    private Function<byte[], ResponseEntity<ByteArrayResource>> buildResponseWithCSV() {
        return csvBytes -> ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new ByteArrayResource(csvBytes));
    }

    private Try<byte[]> writeCSV(List<Tuple2<String, Integer>> entries) {
        return Try.withResources(CSVWithOutput::new).of(printer -> {
            entries.forEach(entry -> {
                try {
                    printer.getCsvPrinter().printRecord(entry._1, entry._2);
                } catch (IOException e) {
                    throw new RuntimeException("Error writing value " + entry._1 + ", " + entry._2);
                }
            });
            printer.getCsvPrinter().flush();
            return printer.getOutput().toByteArray();
        });
    }

}
