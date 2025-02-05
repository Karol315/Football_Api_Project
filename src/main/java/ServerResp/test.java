package ServerResp;

import ServerResp.Responses.SquadsResponse;
import ServerResp.Responses.TeamResponse;
import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.PlayerStatisticsWrapper;
import ServerResp.Wrappers.SquadsWrapper;
import ServerResp.Wrappers.TeamVenueWrapper;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class test {
    public static void main(String[] args) {

        String KEY = "3a8e79e75eef3edc965de9344d41dd01";
//        SquadsWrapper squadsWrapper = new SquadsWrapper(33);
//        System.out.println(squadsWrapper.getTeam());
//        System.out.println(squadsWrapper.getPlayers());
//
//        System.out.println("##############################################################");
//        System.out.println("##############################################################");
//
//
//
//        PlayerStatisticsWrapper playerStatisticsWrapper = new PlayerStatisticsWrapper(squadsWrapper.getPlayers().getFirst().getId());
//        System.out .println(playerStatisticsWrapper.getPlayer());
//        System.out .println(playerStatisticsWrapper.getStatistics().getFirst());


        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://v3.football.api-sports.io/teams?search=Real Madrid")
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", KEY)  // Wstaw swój klucz API
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();

                TeamResponse teamResponse = gson.fromJson(jsonData, TeamResponse.class);
                TeamVenueWrapper teamVenueWrapper = teamResponse.getResponse().getFirst();
                Team team = teamVenueWrapper.getTeam();
                int id = team.id;

            } else {
                throw new RuntimeException("Brak odpowiedzi od serwera");
            }
        } catch (Exception e) {
            throw new RuntimeException("Błąd połączenia: " + e.getMessage());
        }

    }


}

