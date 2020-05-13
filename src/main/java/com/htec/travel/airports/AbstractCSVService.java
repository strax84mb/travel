package com.htec.travel.airports;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractCSVService {

    public long parseDataset(InputStream inputStream) {
        long linesParsed = 0;
        try (var reader = makeReader(inputStream)) {
            for (var fields = reader.readNext(); fields != null; fields = reader.readNext()) {
                if (parseAndSaveLine(fields)) {
                    linesParsed++;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return linesParsed;
    }

    private CSVReader makeReader(InputStream inputStream) {
        var parser = new CSVParserBuilder()
                .withEscapeChar('~') // OpenCSV doesn't support \N null value. Will read it as N char
                .withSeparator(',')
                .withQuoteChar('"')
                .build();
        return new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withKeepCarriageReturn(false)
                .withCSVParser(parser)
                .build();
    }

    public abstract boolean parseAndSaveLine(String[] fields);
}
