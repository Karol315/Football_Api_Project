package ServerResp.Responses;


import com.google.gson.annotations.SerializedName;
import java.util.List;
import ServerResp.Wrappers.*;

public class SquadsResponse {

    @SerializedName("response")
    private List<SquadsWrapper> response;

    // Gettery
    public List<SquadsWrapper> getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "RootResponse{" +
                "response=" + response +
                '}';
    }
}
