package com.qmt.besedo.csv;

import com.qmt.besedo.configuration.CSVReportConfiguration;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public final class CSVWithOutput implements AutoCloseable {

    @Getter
    private final CSVPrinter csvPrinter;
    @Getter
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();


    public CSVWithOutput(CSVReportConfiguration configuration) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(configuration.getId(), configuration.getVowelsCount())
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
