package ScrapeModule.scrapper;


public class Entry {

    private String clubName;

    private int tablePosition;

    private int matchesPlayed;

    private int points;

    private int wins;

    private int draws;

    private int failures;

    private String goalBalance;

    public Entry() {}

    public Entry(String clubName, int tablePosition, int matchesPlayed, int points, int wins, int draws, int failures, String goalBalance) {
        this.clubName = clubName;
        this.tablePosition = tablePosition;
        this.matchesPlayed = matchesPlayed;
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.failures = failures;
        this.goalBalance = goalBalance;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public void setGoalBalance(String goalBalance) {
        this.goalBalance = goalBalance;
    }

    public String getClubName() {
        return clubName;
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getFailures() {
        return failures;
    }

    public String getGoalBalance() {
        return goalBalance;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "clubName='" + clubName + '\'' +
                ", tablePosition=" + tablePosition +
                ", matchesPlayed=" + matchesPlayed +
                ", points=" + points +
                ", wins=" + wins +
                ", draws=" + draws +
                ", failures=" + failures +
                ", goalBalance='" + goalBalance + '\'' +
                '}';
    }
}
