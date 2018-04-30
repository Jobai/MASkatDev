package de.skat3.gui.multiplayermenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import de.skat3.gamelogic.Player;
import de.skat3.io.profile.Profile;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class MultiplayerMenuController {

  @FXML
  private Label serverPlayer;
  @FXML
  private ImageView imageP2;
  @FXML
  private ImageView imageP1;
  @FXML
  private Label serverName;
  @FXML
  private ImageView imageP4;
  @FXML
  private ImageView imageP3;
  @FXML
  private ListView<String> hostListView;
  @FXML
  private Label nameP4;
  @FXML
  private Label nameP3;
  @FXML
  private Label nameP2;
  @FXML
  private Label nameP1;
  @FXML
  private Button refreshButton;
  @FXML
  private Label serverIP;
  @FXML
  private Label serverPW;
  @FXML
  private AnchorPane root;

  Lobby currentLobby;

  ArrayList<Lobby> hostList = new ArrayList<Lobby>();


  public static class HostCell extends ListCell<String> {
    HBox hbox = new HBox();
    Image serverImage = new Image("guifiles/AppIcon.png");
    Image passwordImage = new Image("guifiles/lock.png");
    Image timerImage = new Image("guifiles/alarm-clock.png");
    ImageView ivServer = new ImageView(serverImage);
    ImageView ivPassword = new ImageView(passwordImage);
    ImageView ivTimer = new ImageView(timerImage);
    Pane pane = new Pane();
    Label label = new Label();

    public HostCell() {
      super();
      ivServer.setFitWidth(30);
      ivServer.setFitHeight(30);
      hbox.setPrefHeight(50);
      hbox.setAlignment(Pos.CENTER_LEFT);
      hbox.setSpacing(20);
      label.setFont(new Font(20));
      hbox.getChildren().addAll(ivServer, label, ivPassword, ivTimer, pane);
    }

    public void updateItem(String name, boolean empty) {
      super.updateItem(name, empty);
      setText(null);
      setGraphic(null);

      if (name != null && !empty) {
        label.setText(name);
        setGraphic(hbox);
      }
    }
  }


  /**
   * .
   */
  public MultiplayerMenuController() {

  }

  public void startGame() {
    SkatMain.mainController.startGame();
  }

  /**
   * .
   */
  public void fillHostList() {

    hostListView.setCellFactory(param -> new HostCell());

    Service<String> playService;
    class PlayService extends Service<String> {
      @Override
      protected Task<String> createTask() {
        return new Task<String>() {
          @Override
          protected String call() {
            ObservableList<String> items = FXCollections.observableArrayList();

            hostList = SkatMain.mainController.getLocalHosts();
            for (Lobby lobby : hostList) {
              items.add(lobby.getName() + "(" + lobby.lobbyPlayer + "/"
                  + lobby.getMaximumNumberOfPlayers() + ")");
            }

            hostListView.setItems(items);
            return "True";
          }
        };
      }
    }
    playService = new PlayService();
    playService.restart();
  }

  /**
   * .
   */
  public void hostServer() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HostPopup.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stage = new Stage();
    stage.setTitle("Host Server");
    stage.setScene(new Scene(root));

    HostPopupController hostController = fxmlLoader.getController();
    hostController.setStage(stage);
    stage.show();

  }

  /**
   * .
   */
  public void directConnect() {
    TextInputDialog dialog = new TextInputDialog("");
    dialog.setTitle("Direct Connect");
    dialog.setHeaderText("Direct Connect");
    dialog.setContentText("Please enter Server IP:");
    dialog.initStyle(StageStyle.UTILITY);

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(ip -> SkatMain.mainController.directConnectMultiplayerGame(ip));
  }

  /**
   * .
   */
  public void refresh() {
    showLoadingScreen();
    fillHostList();
  }

  /**
   * .
   */
  public void joinServer() {
    if (!currentLobby.isHasPassword()) {
      SkatMain.mainController.joinMultiplayerGame(currentLobby);
      System.out.println("Join");
    } else { //implemented password input - JB 29.04.2018
      TextInputDialog dialog = new TextInputDialog("");
      dialog.setTitle("Enter lobby password");
      dialog.setHeaderText("Enter lobby password");
      dialog.setContentText("Please enter the lobby password:");
      dialog.initStyle(StageStyle.UTILITY);

      Optional<String> result = dialog.showAndWait();
      result.ifPresent(pass -> SkatMain.mainController.joinMultiplayerGame(currentLobby, pass));
      System.out.println("Join with password");
    }
  }

  /**
   * .
   */
  public void handleHostSelected() {

    try {
      currentLobby = hostList.get(hostListView.getSelectionModel().getSelectedIndex());
    } catch (Exception e) {
      return;
    }

    // fill view fields
    serverName.setText(currentLobby.getName());
    serverIP.setText(currentLobby.getIp().toString());
    // serverPW.setText(currentLobby.);
    serverPlayer.setText(
        currentLobby.lobbyPlayer + "/" + currentLobby.getMaximumNumberOfPlayers());

    // Players
    Player[] players = currentLobby.getPlayers();

    if (players[0] != null) {
      Profile p = SkatMain.ioController.readProfile(players[0].getUuid());
      nameP1.setText(p.getName());
      imageP1.setImage(p.getImage());
    }

    if (players[1] != null) {
      Profile p = SkatMain.ioController.readProfile(players[1].getUuid());
      nameP2.setText(p.getName());
      imageP2.setImage(p.getImage());
    }

    if (players[2] != null) {
      Profile p = SkatMain.ioController.readProfile(players[2].getUuid());
      nameP3.setText(p.getName());
      imageP3.setImage(p.getImage());
    }

    // if (players[3] != null) {
    // Profile p = SkatMain.ioController.readProfile(players[3].getUuid());
    // nameP4.setText(p.getName());
    // imageP4.setImage(p.getImage());
    // }
  }

  /**
   * .
   */
  private void showLoadingScreen() {

    Pane p = new Pane();
    p.setPrefSize(200, 200);

    p.translateXProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(hostListView.translateXProperty())
            .add(hostListView.getWidth() / 2).subtract(100));
    p.translateYProperty()
        .bind(ReadOnlyDoubleProperty.readOnlyDoubleProperty(hostListView.translateYProperty())
            .add(hostListView.getHeight() / 2).subtract(100));

    Image i1 = new Image("guifiles/loading3.gif");
    ImageView v = new ImageView(i1);
    p.getChildren().add(v);
    root.getChildren().add(p);

    PauseTransition pause = new PauseTransition(Duration.seconds(6));
    pause.setOnFinished(event -> root.getChildren().remove(p));
    pause.play();

  }

}
