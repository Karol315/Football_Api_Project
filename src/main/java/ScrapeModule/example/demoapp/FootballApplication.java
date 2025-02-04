package ScrapeModule.example.demoapp;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ScrapeModule.data.DataLoader;
import ScrapeModule.scrapper.Entry;

import java.util.List;

public class FootballApplication extends Application {

    @Override
    public void start(Stage stage) {

        // Wczytanie danych
        //DataLoader.testDataLoader();
        DataLoader.uploadData();
        List<Entry> premierLeagueEntries = DataLoader.loadScrappedData("database/premier_league.csv");
        List<Entry> laligaEntries = DataLoader.loadScrappedData("database/laliga.csv");
        List<Entry> bundesligaEntries = DataLoader.loadScrappedData("database/bundesliga.csv");
        List<Entry> ekstraklasaEntries = DataLoader.loadScrappedData("database/ekstraklasa.csv");


        // Tabele ligowe
        TableView<Entry> premierLeagueTable = createTableView(premierLeagueEntries);
        TableView<Entry> laligaTable = createTableView(laligaEntries);
        TableView<Entry> bundesligaTable = createTableView(bundesligaEntries);
        TableView<Entry> ekstraklasaTable = createTableView(ekstraklasaEntries);

        // Stworzenie zakładek
        TabPane tabPane = new TabPane();

        Tab premierLeagueTab = new Tab("Premier League Table");
        premierLeagueTab.setContent(premierLeagueTable);
        premierLeagueTab.setClosable(false);

        Tab laligaTab = new Tab("LaLiga Table");
        laligaTab.setContent(laligaTable);
        laligaTab.setClosable(false);

        Tab bundesligaTab = new Tab("Bundesliga Table");
        bundesligaTab.setContent(bundesligaTable);
        bundesligaTab.setClosable(false);

        Tab ekstraklasaTab = new Tab("Ekstraklasa Table");
        ekstraklasaTab.setContent(ekstraklasaTable);
        ekstraklasaTab.setClosable(false);

        tabPane.getTabs().addAll(premierLeagueTab, laligaTab, bundesligaTab, ekstraklasaTab);
        // Tu się doda css
        // tabPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        Scene scene = new Scene(tabPane, 1000, 700);
        stage.setTitle("TableView Demo");
        stage.setScene(scene);
        stage.show();
    }

    // Metoda do tworzenia tabeli ligowej
    private static TableView<Entry> createTableView(List<Entry> entries) {
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

    // Metoda do obsłużenia informacji o zespołach
    private void openClubDetails(Entry entry) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Club Details - " + entry.getClubName());

        Label label = new Label("Here would be details of " + entry.getClubName());
        VBox vbox = new VBox(label);
        Scene scene = new Scene(vbox, 300, 200);

        detailsStage.setScene(scene);
        detailsStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}