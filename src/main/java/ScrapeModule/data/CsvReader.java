package ScrapeModule.data;

import ScrapeModule.scrapper.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<Entry> readFromCsv(String fileName) {

        List<Entry> entries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] values;
            boolean isHeader = true;

            while ((values = reader.readNext()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                Entry entry = new Entry(
                        values[0],
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        Integer.parseInt(values[5]),
                        Integer.parseInt(values[6]),
                        values[7]
                );
                entries.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return entries;
    }
}

