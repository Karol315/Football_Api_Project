package ServerResp.Wrappers;

import ServerResp.Responses.SquadsResponse;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import ServerResp.SimpleObjects.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class SquadsWrapper extends ServerResponse{

    @SerializedName("team")
    private Team team;

    @SerializedName("players")
    private List<Player> players;

    // Gettery
    public Team getTeam() { return team; }
    public List<Player> getPlayers() { return players; }


    public SquadsWrapper(int id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://v3.football.api-sports.io/players/squads?team=" + id)
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", KEY)  // Wstaw swój klucz API
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();

                // Parsowanie odpowiedzi
                SquadsResponse squadResponse = gson.fromJson(jsonData, SquadsResponse.class);
                SquadsWrapper squadsWrapper = squadResponse.getResponse().get(0);  // Przyjmujemy, że odpowiedź ma tylko jeden obiekt

                // Wczytanie drużyny i zawodników
                this.team = squadsWrapper.getTeam();
                this.players = squadsWrapper.getPlayers();
            } else {
                throw new RuntimeException("Brak odpowiedzi od serwera");
            }
        } catch (Exception e) {
            throw new RuntimeException("Błąd połączenia: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "team=" + team +
                ", players=" + players +
                '}';
    }

    @Override
    public void fetchDataFromApi(int id) {

    }
}
