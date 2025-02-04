package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BundesligaScrapeTable implements ScrapeTableInterface {
    private final JSoupConnection jSoupConnection;

    public BundesligaScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();

            Element table = document.selectFirst("tbody.ng-star-inserted");
            assert table != null;
            Elements rows = table.select("tr");

            for (Element row : rows) {
                Elements cells = row.select("td");
                int tablePosition = Integer.parseInt(cells.get(1).select("span").getFirst().text());
                String clubName = cells.get(4).select("span").get(2).text();
                int matchesPlayed = Integer.parseInt(cells.get(6).select("span").getFirst().text());
                int wins = Integer.parseInt(cells.get(7).select("span").getFirst().text());
                int draws = Integer.parseInt(cells.get(8).select("span").getFirst().text());
                int failures = Integer.parseInt(cells.get(9).select("span").getFirst().text());
                int points = Integer.parseInt(cells.get(12).select("span").getFirst().text());
                String goalBalance = cells.get(10).select("span").getFirst().text();

                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, goalBalance);
                entries.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
