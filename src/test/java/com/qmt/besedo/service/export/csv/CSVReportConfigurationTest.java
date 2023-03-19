package com.qmt.besedo.service.export.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVReportConfigurationTest {

    @Test
    void getId() {
        assertEquals(";", new CSVReportConfiguration().getDelimiter());
    }

    @Test
    void getVowelsCount() {
        assertEquals("Id", new CSVReportConfiguration().getId());
    }

    @Test
    void getDelimiter() {
        assertEquals("Vowels count", new CSVReportConfiguration().getVowelsCount());
    }
}