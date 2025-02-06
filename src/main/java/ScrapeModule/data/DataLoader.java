package ScrapeModule.data;
import ScrapeModule.scrapper.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataLoader {

    private static final String UPLOAD_DATE = "database/upload_date.csv";

    public static List<Entry> loadScrappedData(String fileName) {
        System.out.println("Załadowano dane z pliku " + fileName);
        return CsvReader.readFromCsv(fileName);
    }

    public static void uploadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(UPLOAD_DATE))) {
            String savedDateTimeStr = reader.readLine();
            if (savedDateTimeStr == null) {
                performScrappingAndSave(LocalDateTime.now());
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime savedDateTime = LocalDateTime.parse(savedDateTimeStr, formatter);
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(savedDateTime, now);
            long minutes = duration.toMinutes();

            // Jesli ostatnia aktualizacja danych była ponad godzinę temu
            // to wykonaj scrapping i zaktualizuj datę.
            if (minutes > 60) {
                performScrappingAndSave(now);
            } else {
                System.out.println("Dane są aktualne.");
            }

        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Błąd parsowania daty: " + e.getMessage());
        }
    }

    private static void performScrappingAndSave(LocalDateTime now) {
        // Ekstraklasa 2024/25
        EkstraklasaScrapeTable scrapeTable1 = new EkstraklasaScrapeTable(new JSoupConnection("https://www.ekstraklasa.org/tabela/sezon"));
        List<Entry> entries1 = scrapeTable1.scrapeLeagueTable();
        CsvWriter.writeToCsv("ekstraklasa.csv", entries1);
        // Premier League 2024/25
        PremierLeagueScrapeTable scrapeTable2 = new PremierLeagueScrapeTable(new JSoupConnection("https://www.premierleague.com/tables?co=1&se=719&ha=-1"));
        List<Entry> entries2 = scrapeTable2.scrapeLeagueTable();
        CsvWriter.writeToCsv("premier_league.csv", entries2);
        // LaLiga 2024/25
        LaLigaScrapeTable scrapeTable3 = new LaLigaScrapeTable(new JSoupConnection("https://www.laliga.com/en-GB/laliga-easports/standing"));
        List<Entry> entries3 = scrapeTable3.scrapeLeagueTable();
        CsvWriter.writeToCsv("laliga.csv", entries3);
        // Bundesliga 2024/25
        BundesligaScrapeTable scrapeTable4 = new BundesligaScrapeTable(new JSoupConnection("https://www.bundesliga.com/en/bundesliga/table"));
        List<Entry> entries4 = scrapeTable4.scrapeLeagueTable();
        CsvWriter.writeToCsv("bundesliga.csv", entries4);
        // Serie A 2024/25
        SerieAScrapeTable scrapeTable5 = new SerieAScrapeTable(new JSoupConnection("https://www.eurosport.com/football/serie-a/2024-2025/standings.shtml"));
        List<Entry> entries5 = scrapeTable5.scrapeLeagueTable();
        CsvWriter.writeToCsv("database/seriea.csv", entries5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try (FileWriter writer = new FileWriter(UPLOAD_DATE)) {
            writer.append(formattedDateTime);
            writer.flush();
            System.out.println("Zapisano datę i godzinę: " + formattedDateTime);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }

    // Metoda do testowania scrappingu danych
    public static void testDataLoader() {
        SerieAScrapeTable scrapeTable = new SerieAScrapeTable(new JSoupConnection("https://www.eurosport.com/football/serie-a/2024-2025/standings.shtml"));
        List<Entry> entries = scrapeTable.scrapeLeagueTable();
        System.out.println(entries);
//        ChampionsLeagueScrapeTable scrapeTable = new ChampionsLeagueScrapeTable(new JSoupConnection("https://www.uefa.com/uefachampionsleague/standings/"));
//        List<Entry> entries = scrapeTable.scrapeLeagueTable();
//        System.out.println(entries);
    }
}
