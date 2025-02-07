package Vizualization.demoapp;

import Vizualization.Exceptions.NoInternetConnectionException;
import Vizualization.Interfaces.TeamFetcher;
import Vizualization.Interfaces.TeamOpener;
import Vizualization.TeamWindow;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

public class FootballApplication extends Application implements TeamOpener {

    private static final String KEY = "3a8e79e75eef3edc965de9344d41dd01";
    Stage currentStage;

    @Override
    public void start(Stage stage) {
        this.currentStage=stage;
        DataLoader.uploadData();
        List<Entry> championsLeagueEntries = DataLoader.loadScrappedData("database/champions_league.csv");
        List<Entry> premierLeagueEntries = DataLoader.loadScrappedData("database/premier_league.csv");
        List<Entry> laligaEntries = DataLoader.loadScrappedData("database/laliga.csv");
        List<Entry> bundesligaEntries = DataLoader.loadScrappedData("database/bundesliga.csv");
        List<Entry> serieAEntries = DataLoader.loadScrappedData("database/seriea.csv");
        List<Entry> leagueOneEntries = DataLoader.loadScrappedData("database/league_one.csv");
        List<Entry> ekstraklasaEntries = DataLoader.loadScrappedData("database/ekstraklasa.csv");
        // DataLoader.testDataLoader();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                createLeagueTab("Champions League", championsLeagueEntries, "file:images/ucl_logo.png"),
                createLeagueTab("Premier League", premierLeagueEntries, "file:images/premier_league_logo.png"),
                createLeagueTab("LaLiga", laligaEntries, "file:images/laliga_logo.png"),
                createLeagueTab("Bundesliga", bundesligaEntries, "file:images/bundesliga_logo.png"),
                createLeagueTab("Serie A", serieAEntries, "file:images/seriea_logo.png"),
                createLeagueTab("Ligue One", leagueOneEntries, "file:images/league_one_logo.png"),
                createLeagueTab("Ekstraklasa", ekstraklasaEntries, "file:images/ekstraklasa_logo.jpg")
        );

        Scene scene = new Scene(tabPane, 1000, 700);
        stage.setTitle("Football League Tables");
        scene.getStylesheets().add(getClass().getResource("/css/football-theme.css").toExternalForm());
        stage.setScene(scene);
        stage.show();


    }

    private Tab createLeagueTab(String leagueName, List<Entry> entries, String logoPath) {
        // Logo
        ImageView logo;
        try {
            logo = new ImageView(new Image(logoPath));
        } catch (Exception e) {
            System.err.println("Błąd ładowania obrazka: " + e.getMessage());
            logo = new ImageView();
        }
        logo.setFitWidth(150);
        logo.setFitHeight(150);
        logo.setPreserveRatio(true);
        HBox logoContainer = new HBox(logo);
        logoContainer.setAlignment(Pos.CENTER);

        TableView<Entry> tableView = createTableView(entries);

        VBox content = new VBox(20);
        content.setPadding(new Insets(20, 20, 20, 20));
        content.getChildren().addAll(logoContainer, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Tab tab = new Tab(leagueName, content);
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

    public void openClubDetails(Entry entry, Stage currentStage) {
        String clubName = entry.getClubName();

        try {
            int teamId = fetchTeamId(clubName);  // Fetch team ID from API

            if (teamId != -1) {
                openTeamWindow(teamId, currentStage);
            } else {
                showErrorPopup("Error", "Team not found: " + clubName);
            }
        } catch (NoInternetConnectionException e) {
            showErrorPopup("Connection Error", e.getMessage());
        } catch (Exception e) {
            showErrorPopup("Unexpected Error", "Something went wrong.");
            e.printStackTrace();
        }
    }

    

    public static void main(String[] args) {
        launch();
    }
}
