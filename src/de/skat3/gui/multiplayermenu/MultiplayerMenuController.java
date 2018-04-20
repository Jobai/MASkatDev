package de.skat3.gui.multiplayermenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import de.skat3.gamelogic.Player;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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

    new Thread() {
      public void run() {
        ObservableList<String> items = FXCollections.observableArrayList("Server 1 | Players (1/3)",
            "Serer 2 | Players (2/4)", "Server 3 | Players (2/4)", "Server 4 | Players (3/4)");

        hostList = SkatMain.mainController.getLocalHosts();
        for (Lobby lobby : hostList) {
          items.add(lobby.getName() + "(" + lobby.getCurrentNumberOfPlayers() + "/"
              + lobby.getMaximumNumberOfPlayers() + ")");
        }

        hostListView.setItems(items);

      };
    }.start();

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
    result.ifPresent(ip -> System.out.println("IP: " + ip));
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
    SkatMain.mainController.joinMultiplayerGame(currentLobby);
    System.out.println("Join");
  }

  /**
   * .
   */
  public void handleHostSelected() {
    System.out.println(hostListView.getSelectionModel().getSelectedItem());
    System.out.println(hostListView.getSelectionModel().getSelectedIndex());

    currentLobby = hostList.get(hostListView.getSelectionModel().getSelectedIndex());

    // fill view fields
    serverName.setText(currentLobby.getName());
    serverIP.setText(currentLobby.getIp().toString());
    // serverPW.setText(currentLobby.);
    serverPlayer.setText(
        currentLobby.getCurrentNumberOfPlayers() + "/" + currentLobby.getMaximumNumberOfPlayers());

    // Players
    Player[] players = currentLobby.getPlayers();

    for (int i = 0; i < players.length; i++) {
      if (i == 0) {
        // nameP1.setText(players[i]);
        // imageP1.setImage(players[i]);
      } ;

      if (i == 1) {
        // nameP2.setText(players[i]);
        // imageP2.setImage(players[i]);
      } ;

      if (i == 2) {
        // nameP3.setText(players[i]);
        // imageP3.setImage(players[i]);
      } ;

      if (i == 3) {
        // nameP4.setText(players[i]);
        // imageP4.setImage(players[i]);
      } ;
    }

  }

  /**
   * .
   */
  private void showLoadingScreen() {

    Platform.runLater(new Runnable() {
      public void run() {
        Stage screen = new Stage();
        Group g = new Group();
        Scene scene = new Scene(g);
        Image i1 = new Image("guifiles/loading.gif");
        ImageView v = new ImageView(i1);
        g.getChildren().add(v);
        screen.setScene(scene);
        screen.setWidth(131);
        screen.setHeight(131);
        screen.setAlwaysOnTop(true);
        screen.initModality(Modality.APPLICATION_MODAL);
        screen.initStyle(StageStyle.UNDECORATED);

        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> screen.close());
        delay.play();

        screen.showAndWait();
      }
    });



  }

}
