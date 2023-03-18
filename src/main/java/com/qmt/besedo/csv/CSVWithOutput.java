package com.qmt.besedo.csv;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

@RequiredArgsConstructor
public final class CSVWithOutput implements AutoCloseable {

    private static final String[] HEADERS = {"id", "Vowels count"}; // TODO should be configurable

    @Getter
    private final CSVPrinter csvPrinter;
    @Getter
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public CSVWithOutput() throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setDelimiter(';')
                .setQuoteMode(QuoteMode.MINIMAL)
                .build();
        this.csvPrinter = new CSVPrinter(new OutputStreamWriter(output), csvFormat);
    }

    @Override
    public void close() throws Exception {
        csvPrinter.close();
        output.close();
    }
}
