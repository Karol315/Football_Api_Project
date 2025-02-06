package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PremierLeagueScrapeTable implements ScrapeTableInterface {
    private final JSoupConnection jSoupConnection;

    public PremierLeagueScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();

            Element table = document.selectFirst("tbody.league-table__tbody.isPL");
            assert table != null;
            Elements rows = table.select("tr");

            for (Element row : rows) {
                if (row.hasClass("league-table_expandable expandable") || row.hasClass("expandable")) {
                    continue;
                }
                int tablePosition = Integer.parseInt(row.attr("data-position"));
                String clubName = row.attr("data-filtered-table-row-name");

                Elements cells = row.select("td");
                int matchesPlayed = Integer.parseInt(cells.get(2).text());
                int wins = Integer.parseInt(cells.get(3).text());
                int draws = Integer.parseInt(cells.get(4).text());
                int failures = Integer.parseInt(cells.get(5).text());
                int points = Integer.parseInt(cells.get(9).text());
                String goalBalance = cells.get(8).text();

                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, goalBalance);
                entries.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
