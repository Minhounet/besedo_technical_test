package com.qmt.besedo.service.export.csv;

import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Class to wrap both {@link CSVPrinter} and the {@link ByteArrayOutputStream} as output.
 */
public final class CSVPrinterWithOutput implements AutoCloseable {

    @Getter
    private final CSVPrinter csvPrinter;
    @Getter
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public CSVPrinterWithOutput(CSVReportConfiguration configuration) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(configuration.getId(), configuration.getVowelsCount())
                .setDelimiter(configuration.getDelimiter())
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
