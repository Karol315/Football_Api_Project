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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerWindow extends Application {

    private int playerId;
    private Stage previousStage;

    public PlayerWindow(int playerId) {
        this.playerId = playerId;
    }

    public PlayerWindow(int playerId, Stage previousStage) {
        this.playerId = playerId;
        this.previousStage = previousStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 15; -fx-alignment: center;");

        Label nameLabel = new Label("Name: Loading...");
        Label ageLabel = new Label("Age: Loading...");
        Label leagueLabel = new Label("League: Loading...");
        Label teamLabel = new Label("Team: Loading...");
        Label goalsLabel = new Label("Goals: Loading...");
        Label assistsLabel = new Label("Assists: Loading...");
        Label yellowCardsLabel = new Label("Yellow Cards: Loading...");
        Label redCardsLabel = new Label("Red Cards: Loading...");
        Label shootingAccuracyLabel = new Label("Shooting Accuracy: Loading...");
        Label minutesPlayedLabel = new Label("Minutes Played: Loading...");

        ImageView playerImageView = new ImageView();
        playerImageView.setFitWidth(100);
        playerImageView.setFitHeight(100);

        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            if (previousStage != null) {
                previousStage.show();
            }
            stage.close();
        });

        vbox.getChildren().addAll(
                playerImageView, nameLabel, ageLabel, leagueLabel, teamLabel, goalsLabel, assistsLabel,
                yellowCardsLabel, redCardsLabel, shootingAccuracyLabel, minutesPlayedLabel, backButton
        );

        InfoBubble infoBubble = new InfoBubble("Here is detailed information about the player from the last completed season");

        // Ustawienie pozycji InfoBubble w rogu okna
        infoBubble.setLayoutX(stage.getWidth() - infoBubble.getWidth() - 10);
        infoBubble.setLayoutY(10);  // 10px od górnej krawędzi

        // Utworzenie głównego AnchorPane
        AnchorPane root = new AnchorPane();

        // Umieszczamy VBox w AnchorPane i ustawiamy, by wypełniał całą przestrzeń
        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);

        // Umieszczamy StackPane w prawym górnym rogu
        AnchorPane.setTopAnchor(infoBubble, 10.0); // Odległość od góry
        AnchorPane.setRightAnchor(infoBubble, 10.0); // Odległość od prawej krawędzi

        // Dodanie obiektów do głównego kontenera
        root.getChildren().addAll(vbox, infoBubble);

        // Ustawiamy scenę na stage – wymiary przekazywane są z obiektu stage
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Player Details");
        stage.show();

        Task<PlayerStatisticsWrapper> task = new Task<>() {
            @Override
            protected PlayerStatisticsWrapper call() throws Exception {
                return new PlayerStatisticsWrapper(playerId);
            }
        };

        task.setOnSucceeded(event -> {
            PlayerStatisticsWrapper wrapper = task.getValue();
            Player player = wrapper.getPlayer();
            Statistics stats = wrapper.getStatistics().getFirst();

            nameLabel.setText("Name: " + player.getName());
            ageLabel.setText("Age: " + player.getAge());
            leagueLabel.setText("League: " + stats.getLeague().getName());
            teamLabel.setText("Team: " + stats.getTeam().getName());
            goalsLabel.setText("Goals: " + stats.getGoals().getTotal());
            assistsLabel.setText("Assists: " + stats.getGoals().getAssists());
            yellowCardsLabel.setText("Yellow Cards: " + stats.getCards().getYellow());
            redCardsLabel.setText("Red Cards: " + stats.getCards().getRed());
            minutesPlayedLabel.setText("Minutes Played: " + stats.getGames().getMinutes());

            int totalShots = stats.getShots().getTotal();
            int shotsOnTarget = stats.getShots().getOn();
            if (totalShots > 0) {
                double accuracy = (shotsOnTarget * 100.0) / totalShots;
                shootingAccuracyLabel.setText(String.format("Shooting Accuracy: %.2f%%", accuracy));
            } else {
                shootingAccuracyLabel.setText("Shooting Accuracy: N/A");
            }

            Image playerImage = new Image(player.getPhoto());
            playerImageView.setImage(playerImage);
        });

        task.setOnFailed(event -> nameLabel.setText("Failed to load player data."));

        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
