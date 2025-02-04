package Vizualization;

import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Statistics;
import ServerResp.Wrappers.PlayerStatisticsWrapper;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerWindow extends Application {

    private int playerId;
    private Stage previousStage; // Opcjonalnie, aby łatwiej wracać do poprzedniego okna

    // Konstruktor przyjmujący playerId (oraz opcjonalnie referencję do poprzedniego okna)
    public PlayerWindow(int playerId) {
        this.playerId = playerId;
    }

    public PlayerWindow(int playerId, Stage previousStage) {
        this.playerId = playerId;
        this.previousStage = previousStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Główny layout
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 15; -fx-alignment: center;");

        // Tworzymy labelki, które później zaktualizujemy
        Label nameLabel = new Label("Name: Loading...");
        Label ageLabel = new Label("Age: Loading...");
        Label teamLabel = new Label("Team: Loading...");
        Label goalsLabel = new Label("Goals: Loading...");
        Label assistsLabel = new Label("Assists: Loading...");

        // Dodajemy obrazek (ImageView)
        ImageView playerImageView = new ImageView();
        playerImageView.setFitWidth(100); // Można zmieniać rozmiar
        playerImageView.setFitHeight(100);

        // Przycisk do powrotu (strzałka wstecz)
        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            // Jeśli posiadamy referencję do poprzedniego okna, pokaż je
            if (previousStage != null) {
                previousStage.show();
            }
            stage.close();
        });

        // Dodajemy wszystkie elementy do layoutu
        vbox.getChildren().addAll(playerImageView, nameLabel, ageLabel, teamLabel, goalsLabel, assistsLabel, backButton);

        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Player Details");
        stage.show();

        // Pobieramy dane o zawodniku w tle
        Task<PlayerStatisticsWrapper> task = new Task<>() {
            @Override
            protected PlayerStatisticsWrapper call() throws Exception {
                return new PlayerStatisticsWrapper(playerId);
            }
        };

        task.setOnSucceeded(event -> {
            PlayerStatisticsWrapper wrapper = task.getValue();
            // Pobieramy dane o zawodniku
            Player player = wrapper.getPlayer();
            // Zakładamy, że lista statystyk nie jest pusta
            Statistics stats = wrapper.getStatistics().getFirst();

            // Aktualizujemy UI
            nameLabel.setText("Name: " + player.getName());
            ageLabel.setText("Age: " + player.getAge());
            // Drużyna pochodzi ze statystyk
            teamLabel.setText("Team: " + stats.getTeam().getName());
            goalsLabel.setText("Goals: " + stats.getGoals().getTotal());
            assistsLabel.setText("Assists: " + stats.getGoals().getAssists());

            // Ustawiamy zdjęcie zawodnika
            Image playerImage = new Image(player.getPhoto());
            playerImageView.setImage(playerImage);
        });

        task.setOnFailed(event -> {
            nameLabel.setText("Failed to load player data.");
        });

        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

