package ServerResp.Responses;

import java.io.Serializable;
import java.util.List;

import ServerResp.Wrappers.TeamVenueWrapper;
import com.google.gson.annotations.SerializedName;

public class TeamResponse implements Serializable {
    @SerializedName("response")
    private List<TeamVenueWrapper> response;

    public List<TeamVenueWrapper> getResponse() {
        return response;
    }
}