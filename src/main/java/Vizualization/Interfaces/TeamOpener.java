package Vizualization.Interfaces;

import ScrapeModule.scrapper.Entry;
import ServerResp.Responses.TeamResponse;
import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.TeamVenueWrapper;
import Vizualization.TeamWindow;
import com.google.gson.Gson;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public interface TeamOpener {
    static final String KEY = "3a8e79e75eef3edc965de9344d41dd01";

    public default void openTeamWindow(int teamId, Stage currentStage) throws Exception {

        TeamWindow teamWindow = new TeamWindow(teamId, currentStage);
        Stage teamStage = new Stage();


        teamStage.setWidth(currentStage.getWidth());
        teamStage.setHeight(currentStage.getHeight());

        teamWindow.start(teamStage);
        currentStage.hide();
    }


    public default int fetchTeamId(String clubName) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        // Wyrażenie lambda implementujące interfejs TeamFetcher
        TeamFetcher tryFetch = (name) -> {
            Request request = new Request.Builder()
                    .url("https://v3.football.api-sports.io/teams?search=" + name)
                    .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                    .addHeader("x-rapidapi-key", KEY)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    String jsonData = response.body().string();
                    TeamResponse teamResponse = gson.fromJson(jsonData, TeamResponse.class);

                    if (teamResponse.getResponse() != null && !teamResponse.getResponse().isEmpty()) {
                        TeamVenueWrapper teamVenueWrapper = teamResponse.getResponse().getFirst();
                        Team team = teamVenueWrapper.getTeam();
                        return team.id;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;  // Jeśli ID nie zostanie znalezione
        };

        // Spróbuj  dla pełnej nazwy klubu
        int teamId = tryFetch.fetch(clubName);
        if (teamId != -1) {
            return teamId;
        }

        // Jeśli klubName składa się z więcej niż jednego słowa, sprawdźmy najdłuższe
        String[] words = clubName.split("\\s+");
        if (words.length > 1) {
            // Znajdź najdłuższe słowo
            String longestWord = "";
            for (String word : words) {
                if (word.length() > longestWord.length()) {
                    longestWord = word;
                }
            }

            teamId = tryFetch.fetch(longestWord);
        }

        return teamId;
    }


    public default void showErrorPopup(String title, String message) {
        Stage errorStage = new Stage();
        errorStage.initModality(Modality.APPLICATION_MODAL);
        errorStage.setTitle(title);

        Label label = new Label(message);
        VBox vbox = new VBox(label);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 300, 100);

        errorStage.setScene(scene);
        errorStage.show();
    }


}
