package Vizualization;

import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Team;
import ServerResp.SimpleObjects.Statistics;
import ServerResp.Wrappers.SquadsWrapper;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.List;

public class TeamWindow extends Application {


    private Stage previousStage;
    private int teamId;

    public TeamWindow(int teamId,  Stage previousStage) {
        this.previousStage = previousStage;
        this.teamId = teamId;
    }

    public void start( Stage stage) throws Exception {
        // Tworzymy główny layout (VBox) z odstępem 20px między elementami
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Layout na logo drużyny i nazwę (na samej górze)
        ImageView teamLogo = new ImageView();
        teamLogo.setFitWidth(200);
        teamLogo.setFitHeight(200);
        Label teamLabel = new Label("Team: ");

        Button backButton = new Button("← Back");
        backButton.setOnAction(e -> {
            if (previousStage != null) {
                previousStage.show();
            }
            stage.close();
        });

        // Layout na zawodników – używamy FlowPane, aby elementy przechodziły do nowej linii, gdy zabraknie miejsca
        FlowPane playersBox = new FlowPane(15, 15);
        playersBox.setStyle("-fx-alignment: center;");

        // Dodajemy elementy do głównego layoutu
        vbox.getChildren().addAll(teamLogo, teamLabel, playersBox, backButton);

        // Pobieranie danych o drużynie i zawodnikach na podstawie przekazanego id
        Task<SquadsWrapper> task = new Task<>() {
            @Override
            protected SquadsWrapper call() throws Exception {
                return new SquadsWrapper(teamId);  // Używamy przekazanego id
            }
        };

        task.setOnSucceeded(event -> {
            SquadsWrapper wrapper = task.getValue();
            Team team = wrapper.getTeam();
            List<Player> players = wrapper.getPlayers();

            // Wyświetlamy dane drużyny
            teamLabel.setText("Team: " + team.getName());
            teamLogo.setImage(new Image(team.getLogo()));

            // Wyświetlamy zawodników
            for (Player player : players) {
                // Tworzymy kontener na zdjęcie zawodnika i podpis (VBox)
                VBox playerBox = new VBox(5);
                playerBox.setStyle("-fx-alignment: center;");

                // Tworzymy ImageView dla zdjęcia zawodnika
                ImageView playerImage = new ImageView(new Image(player.getPhoto()));
                playerImage.setFitWidth(80);
                playerImage.setFitHeight(80);

                // Tworzymy podpis z imieniem i numerem
                Label playerLabel = new Label(player.getName() + "#" + player.getNumber());
                playerLabel.setMaxWidth(100);
                playerLabel.setWrapText(true);
                playerLabel.setStyle("-fx-text-alignment: center;");

                // Dodajemy zdarzenie kliknięcia na zdjęcie – otwieramy okno szczegółów zawodnika
                playerImage.setOnMouseClicked(e -> {
                    try {
                        openPlayerWindow(player.getId(), stage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Dodajemy zdjęcie i podpis do kontenera zawodnika
                playerBox.getChildren().addAll(playerImage, playerLabel);
                // Dodajemy kontener zawodnika do FlowPane
                playersBox.getChildren().add(playerBox);
            }
        });

        task.setOnFailed(event -> {
            teamLabel.setText("Failed to load team and players data.");
        });

        new Thread(task).start();

        InfoBubble infoBubble = new InfoBubble("Kliknij na zawodnika aby zobaczyć dodatkowe informacje");

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
        stage.setTitle("Team and Players Info");
        stage.show();
    }

    /**
     * Metoda otwierająca okno szczegółów zawodnika.
     * Przyjmuje identyfikator zawodnika oraz referencję do obecnego okna.
     */
    private void openPlayerWindow(int playerId, Stage stage) throws Exception {
        // Tworzymy nowe okno szczegółów zawodnika (PlayerWindow pozostaje niezmienione)
        PlayerWindow playerWindow = new PlayerWindow(playerId, stage);
        Stage playerStage = new Stage();

        // Ustawiamy wymiary nowego okna na te same, co obecnego
        playerStage.setWidth(stage.getWidth());
        playerStage.setHeight(stage.getHeight());

        playerWindow.start(playerStage);
        stage.hide(); // Ukrywamy obecne okno
    }



}

