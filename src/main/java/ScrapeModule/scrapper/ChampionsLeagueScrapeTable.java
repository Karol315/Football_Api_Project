package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChampionsLeagueScrapeTable implements ScrapeTableInterface {
    private final JSoupConnection jSoupConnection;

    public ChampionsLeagueScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();
            System.out.println("Mamy dokument");
            Element mainDiv = document.selectFirst("div.ag-center-cols-container");
            assert mainDiv != null;
            Elements clubs = mainDiv.select("div[role=row]");

            for (Element club : clubs) {
                Elements cells = club.select("div[role=gridcell]");
                int tablePosition = Integer.parseInt(cells.getFirst().select("span.pk-text--text-03").getFirst().text());
                System.out.println(tablePosition);
                String clubName = cells.get(0).select("a").attr("title");
                int matchesPlayed = Integer.parseInt(cells.get(1).select("span").getFirst().text());
                int wins = Integer.parseInt(cells.get(6).select("span").getFirst().text());
                int draws = Integer.parseInt(cells.get(7).select("span").getFirst().text());
                int failures = Integer.parseInt(cells.get(8).select("span").getFirst().text());
                int points = Integer.parseInt(cells.get(4).select("span").getFirst().text());
                int scored = Integer.parseInt(cells.get(9).select("span").getFirst().text());
                int conceded = Integer.parseInt(cells.get(10).select("span").getFirst().text());
                String goalBalance = scored + ":" + conceded;

                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, goalBalance);
                entries.add(entry);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return entries;
    }
}
