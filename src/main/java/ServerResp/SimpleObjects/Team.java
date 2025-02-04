package ServerResp.SimpleObjects;

import java.io.Serializable;
import java.io.IOException;
import java.util.List;

import ServerResp.Responses.TeamResponse;
import ServerResp.Wrappers.TeamVenueWrapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Team extends ServerResponse implements Serializable {


    @SerializedName("name")
     String name;

    @SerializedName("code")
     String code;

    @SerializedName("country")
     String country;

    @SerializedName("founded")
     int founded;

    @SerializedName("national")
     boolean national;

    @SerializedName("logo")
    String logo;


    public Team(int id) {
        fetchDataFromApi(id);
    }

    @Override
    public void fetchDataFromApi(int id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://v3.football.api-sports.io/teams?id=" + id)
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", KEY) // Wstaw swój klucz API
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();
                TeamResponse teamResponse = gson.fromJson(jsonData, TeamResponse.class);
                List<TeamVenueWrapper> teamWrappers = teamResponse.getResponse();

                if (!teamWrappers.isEmpty()) {
                    TeamVenueWrapper teamWrapper = teamWrappers.getFirst();
                    Team teamData = teamWrapper.getTeam();

                    this.id = teamData.getId();
                    this.name = teamData.getName();
                    this.code = teamData.getCode();
                    this.country = teamData.getCountry();
                    this.founded = teamData.getFounded();
                    this.national = teamData.isNational();
                    this.logo = teamData.getLogo();
                }
            } else {
                throw new RuntimeException("Brak odpowiedzi od serwera");
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd połączenia: " + e.getMessage());
        }
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getCountry() { return country; }
    public int getFounded() { return founded; }
    public boolean isNational() { return national; }
    public String getLogo() { return logo; }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", country='" + country + '\'' +
                ", founded=" + founded +
                ", national=" + national +
                ", logo='" + logo + '\'' +
                ", id=" + id +
                '}';
    }
}