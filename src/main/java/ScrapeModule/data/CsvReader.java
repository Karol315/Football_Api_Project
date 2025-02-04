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
                // Pomijamy nagłówki
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                Entry entry = new Entry(
                        values[0],                            // clubName
                        Integer.parseInt(values[1]),          // tablePosition
                        Integer.parseInt(values[2]),          // matchesPlayed
                        Integer.parseInt(values[3]),          // points
                        Integer.parseInt(values[4]),          // wins
                        Integer.parseInt(values[5]),          // draws
                        Integer.parseInt(values[6]),          // failures
                        values[7]                             // goalBalance
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

