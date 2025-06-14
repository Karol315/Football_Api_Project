package Vizualization;

import ServerResp.SimpleObjects.Player;
import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.SquadsWrapper;
import Vizualization.Exceptions.NoInternetConnectionException;
import Vizualization.Interfaces.TeamOpener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;

public class TeamWindow extends Application implements TeamOpener {

    private Stage previousStage;
    private int teamId;
    private SquadsWrapper teamWrapper;
    private FlowPane playersBox;
    private List<Player> players;
    private Stage stage;

    public TeamWindow(int teamId, Stage previousStage) {
        this.previousStage = previousStage;
        this.teamId = teamId;
        this.teamWrapper = new SquadsWrapper(teamId);
    }

    public void start(Stage stage) throws Exception {
        this.stage=stage;
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        ImageView teamLogo = new ImageView();
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

        // Sortowanie
        VBox sortBox = new VBox();
        Label sortLabel = new Label("Sort players by:");
        ComboBox<String> sortOptions = new ComboBox<>();
        sortOptions.getItems().addAll("Number", "Age", "Name");
        sortOptions.setValue("Number");
        sortOptions.setOnAction(e -> sortPlayers(sortOptions.getValue()));
        sortBox.getChildren().addAll(sortLabel, sortOptions);

        // Pole wyszukiwania drużyny
        VBox searchBox = new VBox(5);
        Label searchLabel = new Label("Search for another team:");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter a team name...");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(e -> {
            String newTeamName = searchField.getText().trim();
            if (!newTeamName.isEmpty()) {
                try {
                    int newTeamId = fetchTeamId(newTeamName);
                    if (newTeamId != -1) {
                        openTeamWindow(newTeamId, stage);
                    } else {
                        showErrorPopup("Error", "Couldn't find a team: " + newTeamName);
                    }
                }catch (NoInternetConnectionException ex) {
                    showErrorPopup("Connection Error", "No internet connection. Please check your network and try again.");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchBox.getChildren().addAll(searchLabel, searchField, searchButton);

        // Dodanie elementów do głównego VBox
        vbox.getChildren().addAll(teamLogo, teamLabel, playersBox);

        // Pobranie danych drużyny
        Team team = teamWrapper.getTeam();
        players = teamWrapper.getPlayers();

        teamLabel.setText("Team: " + team.getName());
        teamLogo.setImage(new Image(team.getLogo()));

        displayPlayers();

        InfoBubble infoBubble = new InfoBubble("Click on a player image to see additional information");

        // Układ AnchorPane
        AnchorPane root = new AnchorPane();
        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);


        AnchorPane.setTopAnchor(infoBubble, 10.0);
        AnchorPane.setRightAnchor(infoBubble, 10.0);


        VBox  util =  new VBox();
        util.getChildren().addAll(backButton,sortBox,searchBox,searchButton);

        AnchorPane.setLeftAnchor(util, 10.0);
        AnchorPane.setTopAnchor(util, 10.0);

        root.getChildren().addAll(vbox, infoBubble, util);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        scene.getStylesheets().add(getClass().getResource("/css/football-theme.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Team and Players Info");
        stage.show();
    }

    private void sortPlayers(String criteria) {
        switch (criteria) {
            case "Number":
                players.sort(Comparator.comparingInt(Player::getNumber));
                break;
            case "Age":
                players.sort(Comparator.comparingInt(Player::getAge));
                break;
            case "Name":
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

            ImageView playerImage = new ImageView(new Image(player.getPhoto(), true));
            playerImage.setFitWidth(80);
            playerImage.setFitHeight(80);

            Label playerLabel = new Label(player.getName() + "  #" + player.getNumber());
            playerLabel.setMaxWidth(100);
            playerLabel.setWrapText(true);
            playerLabel.setStyle("-fx-text-alignment: center;");

            playerImage.setOnMouseClicked(e -> {
                try {
                    openPlayerWindow(player.getId(),stage );
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            playerBox.getChildren().addAll(playerImage, playerLabel);
            playersBox.getChildren().add(playerBox);
        }
    }

    private void openPlayerWindow(int playerId, Stage stage) throws NoInternetConnectionException{
        try {
            PlayerWindow playerWindow = new PlayerWindow(playerId, stage);
            Stage playerStage = new Stage();
            playerStage.setWidth(stage.getWidth());
            playerStage.setHeight(stage.getHeight());
            playerWindow.start(playerStage);
            stage.hide();
        } catch (NoInternetConnectionException e) {
            showErrorPopup("Connection Error", "No internet connection. Please check your network and try again.");
        } catch (Exception e) {
            showErrorPopup("Error", "Failed to open player details.");
            e.printStackTrace();
        }
    }
}


