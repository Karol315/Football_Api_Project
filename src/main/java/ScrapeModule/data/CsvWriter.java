package ScrapeModule.data;

import ScrapeModule.scrapper.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVWriter;

public class CsvWriter {
    public static void writeToCsv(String fileName, List<Entry> entries) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            // Nagłówki
            String[] header = {"clubName", "tablePosition", "matchesPlayed", "points", "wins", "draws", "failures", "goalBalance"};
            writer.writeNext(header);

            // Dane
            for (Entry entry : entries) {
                String[] data = {
                        entry.getClubName(),
                        String.valueOf(entry.getTablePosition()),
                        String.valueOf(entry.getMatchesPlayed()),
                        String.valueOf(entry.getPoints()),
                        String.valueOf(entry.getWins()),
                        String.valueOf(entry.getDraws()),
                        String.valueOf(entry.getFailures()),
                        entry.getGoalBalance()
                };
                writer.writeNext(data);
            }
            System.out.println("Zapisano dane do pliku " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

