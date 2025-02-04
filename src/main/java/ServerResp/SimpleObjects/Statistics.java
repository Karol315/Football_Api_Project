package ServerResp.SimpleObjects;



import com.google.gson.annotations.SerializedName;

public class Statistics extends ServerResponse{

    @SerializedName("team")
    private Team team;

    @SerializedName("league")
    private League league;

    @SerializedName("games")
    private Games games;

    @SerializedName("substitutes")
    private Substitutes substitutes;

    @SerializedName("shots")
    private Shots shots;

    @SerializedName("goals")
    private Goals goals;

    @SerializedName("passes")
    private Passes passes;

    @SerializedName("tackles")
    private Tackles tackles;

    @SerializedName("duels")
    private Duels duels;

    @SerializedName("dribbles")
    private Dribbles dribbles;

    @SerializedName("fouls")
    private Fouls fouls;

    @SerializedName("cards")
    private Cards cards;

    @SerializedName("penalty")
    private Penalty penalty;

    // Gettery
    public Team getTeam() { return team; }
    public League getLeague() { return league; }
    public Games getGames() { return games; }
    public Substitutes getSubstitutes() { return substitutes; }
    public Shots getShots() { return shots; }
    public Goals getGoals() { return goals; }
    public Passes getPasses() { return passes; }
    public Tackles getTackles() { return tackles; }
    public Duels getDuels() { return duels; }
    public Dribbles getDribbles() { return dribbles; }
    public Fouls getFouls() { return fouls; }
    public Cards getCards() { return cards; }
    public Penalty getPenalty() { return penalty; }

    @Override
    public String toString() {
        return "Statistics{" +
                "team=" + team +
                ", league=" + league +
                ", games=" + games +
                ", substitutes=" + substitutes +
                ", shots=" + shots +
                ", goals=" + goals +
                ", passes=" + passes +
                ", tackles=" + tackles +
                ", duels=" + duels +
                ", dribbles=" + dribbles +
                ", fouls=" + fouls +
                ", cards=" + cards +
                ", penalty=" + penalty +
                '}';
    }

    @Override
    public void fetchDataFromApi(int id) {

    }

    // Podklasy

    public static class Games {
        @SerializedName("appearences")
        private int appearances;

        @SerializedName("lineups")
        private int lineups;

        @SerializedName("minutes")
        private int minutes;

        @SerializedName("number")
        private Integer number;

        @SerializedName("position")
        private String position;

        @SerializedName("rating")
        private String rating;

        @SerializedName("captain")
        private boolean captain;

        // Gettery
        public int getAppearances() { return appearances; }
        public int getLineups() { return lineups; }
        public int getMinutes() { return minutes; }
        public Integer getNumber() { return number; }
        public String getPosition() { return position; }
        public String getRating() { return rating; }
        public boolean isCaptain() { return captain; }

        @Override
        public String toString() {
            return "Games{" +
                    "appearances=" + appearances +
                    ", lineups=" + lineups +
                    ", minutes=" + minutes +
                    ", number=" + number +
                    ", position='" + position + '\'' +
                    ", rating='" + rating + '\'' +
                    ", captain=" + captain +
                    '}';
        }
    }

    public static class Substitutes {
        @SerializedName("in")
        private int in;

        @SerializedName("out")
        private int out;

        @SerializedName("bench")
        private int bench;

        // Gettery
        public int getIn() { return in; }
        public int getOut() { return out; }
        public int getBench() { return bench; }

        @Override
        public String toString() {
            return "Substitutes{" +
                    "in=" + in +
                    ", out=" + out +
                    ", bench=" + bench +
                    '}';
        }
    }

    public static class Shots {
        @SerializedName("total")
        private int total;

        @SerializedName("on")
        private int on;

        // Gettery
        public int getTotal() { return total; }
        public int getOn() { return on; }

        @Override
        public String toString() {
            return "Shots{" +
                    "total=" + total +
                    ", on=" + on +
                    '}';
        }
    }

    public static class Goals {
        @SerializedName("total")
        private int total;

        @SerializedName("conceded")
        private Integer conceded;

        @SerializedName("assists")
        private int assists;

        @SerializedName("saves")
        private int saves;

        // Gettery
        public int getTotal() { return total; }
        public Integer getConceded() { return conceded; }
        public int getAssists() { return assists; }
        public int getSaves() { return saves; }

        @Override
        public String toString() {
            return "Goals{" +
                    "total=" + total +
                    ", conceded=" + conceded +
                    ", assists=" + assists +
                    ", saves=" + saves +
                    '}';
        }
    }

    public static class Passes {
        @SerializedName("total")
        private int total;

        @SerializedName("key")
        private int key;

        @SerializedName("accuracy")
        private int accuracy;

        // Gettery
        public int getTotal() { return total; }
        public int getKey() { return key; }
        public int getAccuracy() { return accuracy; }

        @Override
        public String toString() {
            return "Passes{" +
                    "total=" + total +
                    ", key=" + key +
                    ", accuracy=" + accuracy +
                    '}';
        }
    }

    public static class Tackles {
        @SerializedName("total")
        private int total;

        @SerializedName("blocks")
        private int blocks;

        @SerializedName("interceptions")
        private int interceptions;

        // Gettery
        public int getTotal() { return total; }
        public int getBlocks() { return blocks; }
        public int getInterceptions() { return interceptions; }

        @Override
        public String toString() {
            return "Tackles{" +
                    "total=" + total +
                    ", blocks=" + blocks +
                    ", interceptions=" + interceptions +
                    '}';
        }
    }

    public static class Duels {
        @SerializedName("total")
        private Integer total;

        @SerializedName("won")
        private Integer won;

        // Gettery
        public Integer getTotal() { return total; }
        public Integer getWon() { return won; }

        @Override
        public String toString() {
            return "Duels{" +
                    "total=" + total +
                    ", won=" + won +
                    '}';
        }
    }

    public static class Dribbles {
        @SerializedName("attempts")
        private int attempts;

        @SerializedName("success")
        private int success;

        // Gettery
        public int getAttempts() { return attempts; }
        public int getSuccess() { return success; }

        @Override
        public String toString() {
            return "Dribbles{" +
                    "attempts=" + attempts +
                    ", success=" + success +
                    '}';
        }
    }

    public static class Fouls {
        @SerializedName("drawn")
        private int drawn;

        @SerializedName("committed")
        private int committed;

        // Gettery
        public int getDrawn() { return drawn; }
        public int getCommitted() { return committed; }

        @Override
        public String toString() {
            return "Fouls{" +
                    "drawn=" + drawn +
                    ", committed=" + committed +
                    '}';
        }
    }

    public static class Cards {
        @SerializedName("yellow")
        private int yellow;

        @SerializedName("yellowred")
        private int yellowRed;

        @SerializedName("red")
        private int red;

        // Gettery
        public int getYellow() { return yellow; }
        public int getYellowRed() { return yellowRed; }
        public int getRed() { return red; }

        @Override
        public String toString() {
            return "Cards{" +
                    "yellow=" + yellow +
                    ", yellowRed=" + yellowRed +
                    ", red=" + red +
                    '}';
        }
    }

    public static class Penalty {
        @SerializedName("won")
        private int won;

        @SerializedName("commited")
        private Integer committed;

        @SerializedName("scored")
        private int scored;

        @SerializedName("missed")
        private int missed;

        @SerializedName("saved")
        private Integer saved;

        // Gettery
        public int getWon() { return won; }
        public Integer getCommitted() { return committed; }
        public int getScored() { return scored; }
        public int getMissed() { return missed; }
        public Integer getSaved() { return saved; }

        @Override
        public String toString() {
            return "Penalty{" +
                    "won=" + won +
                    ", committed=" + committed +
                    ", scored=" + scored +
                    ", missed=" + missed +
                    ", saved=" + saved +
                    '}';
        }
    }
}