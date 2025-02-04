package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerieAScrapeTable implements ScrapeTableInterface {
    private final JSoupConnection jSoupConnection;

    public SerieAScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    @Override
    public List<Entry> scrapeLeagueTable() {
        List<Entry> entries = new ArrayList<>();

        try {
            Document document = jSoupConnection.getDocument();
            System.out.println(document.html());

//            Element table = document.select("div.row").select("div.hm-components-with-title").get(1);
//            assert table != null;
//            Elements rows = table.select("tbody.hm-tbody").select("tr");
//            System.out.println(rows.size());
//
//            for (Element row : rows) {
//                if (row.hasClass("hm-collapse-table collapse") || row.hasClass("collapse")) {
//                    continue;
//                }
//
//                Elements cells = row.select("td");
//                System.out.println(cells.size());
//                int tablePosition = Integer.parseInt(cells.get(0).select("p").getFirst().text());
//                System.out.println(tablePosition);
//                String clubName = cells.get(1).select("p").getFirst().text();
//                System.out.println(clubName);
//                int matchesPlayed = Integer.parseInt(cells.get(3).select("p").getFirst().text());
//                int wins = Integer.parseInt(cells.get(4).select("p").getFirst().text());
//                int draws = Integer.parseInt(cells.get(5).select("p").getFirst().text());
//                int failures = Integer.parseInt(cells.get(6).select("p").getFirst().text());
//                int points = Integer.parseInt(cells.get(2).select("p").getFirst().text());
//
//                Entry entry = new Entry(clubName, tablePosition, matchesPlayed, points, wins, draws, failures, "0:0");
//                entries.add(entry);
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
