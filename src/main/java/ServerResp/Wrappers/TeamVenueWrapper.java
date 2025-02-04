package ServerResp.Wrappers;

import java.io.Serializable;

import ServerResp.SimpleObjects.Team;
import ServerResp.SimpleObjects.Venue;
import com.google.gson.annotations.SerializedName;

public class TeamVenueWrapper implements Serializable {
    @SerializedName("team")
    private Team team;

    @SerializedName("venue")
    private Venue venue;

    public Team getTeam() {
        return team;
    }

    public Venue getVenue() {
        return venue;
    }
}