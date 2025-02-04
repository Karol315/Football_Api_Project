package ServerResp.Wrappers;



import ServerResp.Responses.PlayerStatisticsResponse;
import ServerResp.SimpleObjects.*;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class PlayerStatisticsWrapper {

    @SerializedName("player")
    private Player player;

    @SerializedName("statistics")
    private List<Statistics> statistics;

    // Konstruktor przyjmujący ID gracza
    public PlayerStatisticsWrapper(int playerId) {
        OkHttpClient client = new OkHttpClient();

        // Budowanie zapytania HTTP do API
        Request request = new Request.Builder()
                .url("https://v3.football.api-sports.io/players?id=" + playerId + "&season=2023")
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", "3a8e79e75eef3edc965de9344d41dd01")  // Wstaw swój klucz API
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();

                // Deserializacja odpowiedzi JSON do obiektów
                Gson gson = new Gson();
                PlayerStatisticsResponse playerStatisticsResponse = gson.fromJson(jsonData, PlayerStatisticsResponse.class);


                this.player = playerStatisticsResponse.getResponse().getFirst().getPlayer();
                this.statistics = playerStatisticsResponse.getResponse().getFirst().getStatistics();
            } else {
                throw new RuntimeException("Brak odpowiedzi od serwera");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd połączenia: " + e.getMessage());
        }
    }

    // Gettery
    public Player getPlayer() {
        return player;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    // Metoda toString dla debugowania
    @Override
    public String toString() {
        return "PlayerStatisticsWrapper{" +
                "player=" + player +
                ", statistics=" + statistics +
                '}';
    }
}
