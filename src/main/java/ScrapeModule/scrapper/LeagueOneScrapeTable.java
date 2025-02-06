package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeagueOneScrapeTable implements ScrapeTableInterface {
    private final JSoupConnection jSoupConnection;

    public LeagueOneScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();

            Element table = document.select("tbody.w-full.divide-y.divide-br-2-80").getFirst();
            assert table != null;
            Elements rows = table.select("tr.bg-br-2-100");

            for (Element row : rows) {
                Elements cells = row.select("td");

                int tablePosition = Integer.parseInt(cells.get(1).text());
                String clubName = cells.get(3).select("a").getFirst().text();
                int matchesPlayed = Integer.parseInt(cells.get(5).text());
                int wins = Integer.parseInt(cells.get(6).text());
                int draws = Integer.parseInt(cells.get(7).text());
                int failures = Integer.parseInt(cells.get(8).text());
                int points = Integer.parseInt(cells.get(12).text());
                String goalBalance = cells.get(11).text();

                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, goalBalance);
                entries.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
