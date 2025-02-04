package Vizualization;

import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.SquadsWrapper;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.List;

public class TeamWindow extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        // Tworzymy główny layout
        VBox vbox = new VBox(20); // Odstęp między elementami (pionowy)
        vbox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Tworzymy layout na logo drużyny na samej górze
        ImageView teamLogo = new ImageView();
        teamLogo.setFitWidth(200);
        teamLogo.setFitHeight(200);
        Label teamLabel = new Label("Team: ");

        // Tworzymy layout na zawodników, używając FlowPane do automatycznego przechodzenia do nowej linii
        FlowPane playersBox = new FlowPane(15, 15); // Odstęp między zawodnikami (poziomo i pionowo)
        playersBox.setStyle("-fx-alignment: center;");

        // Dodajemy logo drużyny do głównego layoutu
        vbox.getChildren().addAll(teamLogo, teamLabel, playersBox);

        // Rozpoczynamy pobieranie danych o drużynie i zawodnikach w tle
        Task<SquadsWrapper> task = new Task<>() {
            @Override
            protected SquadsWrapper call() throws Exception {
                return new SquadsWrapper(85); // Przykład z ID drużyny PSG (85)
            }
        };

        // Po zakończeniu zadania, zaktualizuj interfejs
        task.setOnSucceeded(event -> {
            SquadsWrapper wrapper = task.getValue();
            Team team = wrapper.getTeam();
            List<Player> players = wrapper.getPlayers();

            // Wyświetlamy dane drużyny
            teamLabel.setText("Team: " + team.getName());
            teamLogo.setImage(new Image(team.getLogo()));

            // Wyświetlamy zawodników
            for (Player player : players) {
                // Tworzymy kontener na zdjęcie i podpis
                VBox playerBox = new VBox(5); // Odstęp między zdjęciem a podpisem
                playerBox.setStyle("-fx-alignment: center;");

                // Tworzymy zdjęcie zawodnika
                ImageView playerImage = new ImageView();
                playerImage.setImage(new Image(player.getPhoto()));
                playerImage.setFitWidth(80);
                playerImage.setFitHeight(80);

                // Tworzymy podpis (imię i numer)
                Label playerLabel = new Label(player.getName() + " (#" + player.getNumber() + ")");
                playerLabel.setMaxWidth(100); // Ograniczenie szerokości podpisu, aby się mieścił
                playerLabel.setWrapText(true); // Umożliwia zawijanie tekstu, jeśli nie mieści się w szerokości
                playerLabel.setStyle("-fx-text-alignment: center;");

                // Dodajemy zdarzenie kliknięcia na zdjęcie
                playerImage.setOnMouseClicked(e -> {
                    try {
                        openPlayerWindow(player,stage);  // Otwórz szczegóły zawodnika
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Dodajemy zdjęcie i podpis do kontenera
                playerBox.getChildren().addAll(playerImage, playerLabel);

                // Dodajemy kontener z zawodnikiem do layoutu
                playersBox.getChildren().add(playerBox);
            }
        });

        // Obsługuje wyjątki w przypadku błędów podczas pobierania danych
        task.setOnFailed(event -> {
            teamLabel.setText("Failed to load team and players data.");
        });

        // Uruchamiamy wątek w tle
        new Thread(task).start();

        // Tworzymy scenę z layoutem i ustawiamy ją na stage
        Scene scene = new Scene(vbox, 600, 400); // Dostosowanie rozmiaru
        stage.setScene(scene);
        stage.setTitle("Team and Players Info");
        stage.show();
    }

    private void openPlayerWindow(Player player, Stage stage) throws Exception {
        // Tworzymy nowe okno szczegółów zawodnika
        PlayerWindow playerWindow = new PlayerWindow(player.getId(), stage);
        Stage playerStage = new Stage();

        // Ustawiamy wymiary nowego okna na wymiary obecnego okna
        playerStage.setWidth(stage.getWidth());
        playerStage.setHeight(stage.getHeight());

        playerWindow.start(playerStage);
        stage.hide(); // Ukrywamy okno drużyny
    }

    public static void main(String[] args) {
        launch(args);
    }
}

