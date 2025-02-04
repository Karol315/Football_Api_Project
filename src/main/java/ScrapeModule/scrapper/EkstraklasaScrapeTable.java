package ScrapeModule.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EkstraklasaScrapeTable implements ScrapeTableInterface {

    private final JSoupConnection jSoupConnection;

    public EkstraklasaScrapeTable(JSoupConnection jSoupConnection) {
        this.jSoupConnection = jSoupConnection;
    }

    public List<Entry> scrapeLeagueTable(){

        List<Entry> entries = new ArrayList<>();
        int counter = 1;

        try {
            Document document = jSoupConnection.getDocument();
            Element mainDiv = document.selectFirst("div.grid.text-black");

            assert mainDiv != null;
            Elements clubs = mainDiv.select("app-league-standings-entry");

            for (Element club : clubs) {
                Entry entry = createEntry(club, counter++);

                if (entry != null) {
                    entries.add(entry);
                }
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return entries;
        }

        private Entry createEntry(Element club, int tablePosition) {
            Entry entry = new Entry();

            Element clubNameElement = club.selectFirst("span.hidden.lg\\:inline");
            if (clubNameElement != null) {
                String clubName = clubNameElement.text();
                entry.setClubName(clubName);
            }

            Elements teamStats = club.select("div.standings-data span");
            if (teamStats.size() >= 6) {
                try {
                    parseStandingsData(entry, teamStats, tablePosition);
                } catch (NumberFormatException e) {
                    System.out.println("Error while parsing values: " + e.getMessage());
                    return null;
                }
            }

            return entry;
        }

        private void parseStandingsData(Entry entry, Elements teamStats, int tablePosition) {
            entry.setTablePosition(tablePosition);
            entry.setMatchesPlayed(Integer.parseInt(teamStats.get(0).text()));
            entry.setPoints(Integer.parseInt(teamStats.get(1).text()));
            entry.setWins(Integer.parseInt(teamStats.get(2).text()));
            entry.setDraws(Integer.parseInt(teamStats.get(3).text()));
            entry.setFailures(Integer.parseInt(teamStats.get(4).text()));
            entry.setGoalBalance(teamStats.get(5).text());
        }
}
