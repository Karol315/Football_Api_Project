package ServerResp.Responses;
import ServerResp.Wrappers.PlayerStatisticsWrapper;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class PlayerStatisticsResponse {

        @SerializedName("response")
        private List<PlayerStatisticsWrapper> response;

        public List<PlayerStatisticsWrapper> getResponse() {
            return response;
        }

        public void setResponse(List<PlayerStatisticsWrapper> response) {
            this.response = response;
        }
}
