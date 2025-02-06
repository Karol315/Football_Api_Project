package Vizualization;

import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.SquadsWrapper;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class TeamWindow extends Application {

    private Stage previousStage;
    private int teamId;
    private SquadsWrapper teamWrapper;
    private FlowPane playersBox;
    private List<Player> players;

    public TeamWindow(int teamId, Stage previousStage) {
        this.previousStage = previousStage;
        this.teamId = teamId;
        this.teamWrapper = new SquadsWrapper(teamId);
    }

    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        ImageView teamLogo = new ImageView();
        teamLogo.setFitWidth(200);
        teamLogo.setFitHeight(200);
        Label teamLabel = new Label("Team: ");

        Button backButton = new Button("\u2190 Back");
        backButton.setOnAction(e -> {
            if (previousStage != null) {
                previousStage.show();
            }
            stage.close();
        });

        playersBox = new FlowPane(15, 15);
        playersBox.setStyle("-fx-alignment: center;");

        VBox sortBox = new VBox(); // Tworzymy kontener dla etykiety i ComboBoxa

        Label sortLabel = new Label("Sortuj zawodników według:");
        ComboBox<String> sortOptions = new ComboBox<>();
        sortOptions.getItems().addAll("Numer koszulki", "Wiek", "Nazwisko");
        sortOptions.setValue("Numer koszulki");
        sortOptions.setOnAction(e -> sortPlayers(sortOptions.getValue()));

        sortBox.getChildren().addAll(sortLabel, sortOptions); // Dodajemy elementy do kontenera

        vbox.getChildren().addAll(teamLogo, teamLabel, playersBox);

        Team team = teamWrapper.getTeam();
        players = teamWrapper.getPlayers();

        teamLabel.setText("Team: " + team.getName());
        teamLogo.setImage(new Image(team.getLogo()));

        displayPlayers();

        InfoBubble infoBubble = new InfoBubble("Kliknij na zawodnika aby zobaczyć dodatkowe informacje");

        AnchorPane root = new AnchorPane();
        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);

        AnchorPane.setTopAnchor(infoBubble, 10.0);
        AnchorPane.setRightAnchor(infoBubble, 10.0);

        AnchorPane.setLeftAnchor(sortBox,10.0);
        AnchorPane.setTopAnchor(sortBox,45.0);

        AnchorPane.setLeftAnchor(backButton,10.0);
        AnchorPane.setTopAnchor(backButton,10.0);

        root.getChildren().addAll(vbox, infoBubble,sortBox,backButton);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Team and Players Info");
        stage.show();
    }

    private void sortPlayers(String criteria) {
        switch (criteria) {
            case "Numer koszulki":
                players.sort(Comparator.comparingInt(Player::getNumber));
                break;
            case "Wiek":
                players.sort(Comparator.comparingInt(Player::getAge));
                break;
            case "Imię":
                players.sort(Comparator.comparing(Player::getName));
                break;
        }
        displayPlayers();
    }

    private void displayPlayers() {
        playersBox.getChildren().clear();
        for (Player player : players) {
            VBox playerBox = new VBox(5);
            playerBox.setStyle("-fx-alignment: center;");

            ImageView playerImage = new ImageView(new Image(player.getPhoto()));
            playerImage.setFitWidth(80);
            playerImage.setFitHeight(80);

            Label playerLabel = new Label(player.getName() + "  #" + player.getNumber());
            playerLabel.setMaxWidth(100);
            playerLabel.setWrapText(true);
            playerLabel.setStyle("-fx-text-alignment: center;");

            playerImage.setOnMouseClicked(e -> {
                try {
                    openPlayerWindow(player.getId(), previousStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            playerBox.getChildren().addAll(playerImage, playerLabel);
            playersBox.getChildren().add(playerBox);
        }
    }

    private void openPlayerWindow(int playerId, Stage stage) throws Exception {
        PlayerWindow playerWindow = new PlayerWindow(playerId, stage);
        Stage playerStage = new Stage();
        playerStage.setWidth(stage.getWidth());
        playerStage.setHeight(stage.getHeight());
        playerWindow.start(playerStage);
        stage.hide();
    }
}


