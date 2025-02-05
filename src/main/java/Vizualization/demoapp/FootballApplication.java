package Vizualization.demoapp;

import Vizualization.PlayerWindow;
import Vizualization.TeamWindow;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ScrapeModule.data.DataLoader;
import ScrapeModule.scrapper.Entry;
import ServerResp.Responses.TeamResponse;
import ServerResp.Wrappers.TeamVenueWrapper;
import ServerResp.SimpleObjects.Team;

import java.util.List;

public class FootballApplication extends Application {

    private static final String KEY = "3a8e79e75eef3edc965de9344d41dd01";  // Wstaw swój klucz API
    Stage currentStage;

    @Override
    public void start(Stage stage) {
        this.currentStage=stage;
        DataLoader.uploadData();
        List<Entry> premierLeagueEntries = DataLoader.loadScrappedData("database/premier_league.csv");
        List<Entry> laligaEntries = DataLoader.loadScrappedData("database/laliga.csv");
        List<Entry> bundesligaEntries = DataLoader.loadScrappedData("database/bundesliga.csv");
        List<Entry> ekstraklasaEntries = DataLoader.loadScrappedData("database/ekstraklasa.csv");

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                createLeagueTab("Premier League", premierLeagueEntries),
                createLeagueTab("LaLiga", laligaEntries),
                createLeagueTab("Bundesliga", bundesligaEntries),
                createLeagueTab("Ekstraklasa", ekstraklasaEntries)
        );

        Scene scene = new Scene(tabPane, 1000, 700);
        stage.setTitle("Football League Tables");
        stage.setScene(scene);
        stage.show();


    }

    private Tab createLeagueTab(String leagueName, List<Entry> entries) {
        TableView<Entry> tableView = createTableView(entries);
        Tab tab = new Tab(leagueName, tableView);
        tab.setClosable(false);

        // Dodajemy obsługę kliknięcia na wiersz tabeli
        tableView.setRowFactory(tv -> {
            TableRow<Entry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Entry clickedEntry = row.getItem();
                    openClubDetails(clickedEntry, currentStage);
                }
            });
            return row;
        });

        return tab;
    }

    private TableView<Entry> createTableView(List<Entry> entries) {
        TableView<Entry> table = new TableView<Entry>();

        TableColumn<Entry, String> clubNameCol = new TableColumn<>("Club Name");
        clubNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClubName()));

        TableColumn<Entry, Integer> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTablePosition()).asObject());

        TableColumn<Entry, Integer> matchesCol = new TableColumn<>("Matches");
        matchesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMatchesPlayed()).asObject());

        TableColumn<Entry, Integer> pointsCol = new TableColumn<>("Points");
        pointsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPoints()).asObject());

        TableColumn<Entry, Integer> winsCol = new TableColumn<>("Wins");
        winsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWins()).asObject());

        TableColumn<Entry, Integer> drawsCol = new TableColumn<>("Draws");
        drawsCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDraws()).asObject());

        TableColumn<Entry, Integer> failuresCol = new TableColumn<>("Failures");
        failuresCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFailures()).asObject());

        TableColumn<Entry, String> goalBalanceCol = new TableColumn<>("Goal Balance");
        goalBalanceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoalBalance()));

        table.getColumns().addAll(positionCol, clubNameCol, matchesCol, pointsCol, winsCol, drawsCol, failuresCol, goalBalanceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setItems(FXCollections.observableArrayList(entries));

        return table;
    }

    private void openClubDetails(Entry entry, Stage parentStage) {
        String clubName = entry.getClubName();
        int teamId = fetchTeamId(clubName);  // Pobranie ID zespołu z API

        if (teamId != -1) {
            try {
                openTeamWindow(teamId, currentStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showErrorPopup("Błąd", "Nie udało się znaleźć zespołu: " + clubName);
        }
    }

    private int fetchTeamId(String clubName) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://v3.football.api-sports.io/teams?search=" + clubName)
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
    }

    private void showErrorPopup(String title, String message) {
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

    private void openTeamWindow(int teamId, Stage stage) throws Exception {

        TeamWindow teamWindow = new TeamWindow(teamId, currentStage);
        Stage teamStage = new Stage();

        // Ustawiamy wymiary nowego okna na te same, co obecnego
        teamStage.setWidth(stage.getWidth());
        teamStage.setHeight(stage.getHeight());

        teamWindow.start(teamStage);
        currentStage.hide(); // Ukrywamy obecne okno
    }

    public static void main(String[] args) {
        launch();
    }
}
