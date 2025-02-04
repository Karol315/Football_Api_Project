package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LaLigaScrapeTable implements ScrapeTableInterface {

    private final JSoupConnection jSoupConnection;

    public LaLigaScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();

            Element mainDiv = document.selectFirst("div.styled__StandingTableBody-sc-e89col-5.kIvDwR");
            assert mainDiv != null;
            Elements rows = mainDiv.select("div.styled__StandingTabBody-sc-e89col-0.isRHqh");

            for (Element row : rows) {
                int tablePosition = Integer.parseInt(row.select("div.styled__Td-sc-e89col-10.fTFWtb p").getFirst().text());
                System.out.println(tablePosition);
                String clubName = row.select("div.styled__ShieldContainer-sc-1opls7r-0.eIaTDi.shield-desktop p").getFirst().text();
                System.out.println(clubName);

                Elements cells = row.select("div.styled__Td-sc-e89col-10.feNufd");
                int matchesPlayed = Integer.parseInt(cells.get(1).select("p").getFirst().text());
                int wins = Integer.parseInt(cells.get(2).select("p").getFirst().text());
                int draws = Integer.parseInt(cells.get(3).select("p").getFirst().text());
                int failures = Integer.parseInt(cells.get(4).select("p").getFirst().text());
                int points = Integer.parseInt(cells.get(0).select("p").getFirst().text());

                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, "0:0");
                entries.add(entry);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
