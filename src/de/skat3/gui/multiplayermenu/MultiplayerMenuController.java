package de.skat3.gui.multiplayermenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import de.skat3.main.Lobby;
import de.skat3.main.SkatMain;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
  private Button refreshButton;
  @FXML
  private Label serverName;
  @FXML
  private Label serverIP;
  @FXML
  private Label serverPW;
  @FXML
  private ListView<String> hostListView;

  ArrayList<Lobby> hostList = new ArrayList<Lobby>();

  /**
   * .
   */
  public MultiplayerMenuController() {

  }

  /**
   * .
   */
  public void fillHostList() {
    ObservableList<String> items = FXCollections.observableArrayList();

    new Thread() {
      public void run() {
        hostList = SkatMain.mainController.getLocalHosts();
      };
    }.start();

    for (Lobby lobby : hostList) {
      items.add(lobby.getName() + "(" + lobby.getCurrentNumberOfPlayers() + "/"
          + lobby.getMaximumNumberOfPlayers() + ")");
    }

    hostListView.setItems(items);
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

  }

  /**
   * .
   */
  public void handleHostSelected() {
    System.out.println(hostListView.getSelectionModel().getSelectedItem());
    System.out.println(hostListView.getSelectionModel().getSelectedIndex());

    Lobby currentLobby = hostList.get(hostListView.getSelectionModel().getSelectedIndex());

    // fill view fields
    serverName.setText(currentLobby.getName());
    serverIP.setText(currentLobby.getIp().toString());
    // serverPW.setText(currentLobby);
    serverPlayer.setText(
        currentLobby.getCurrentNumberOfPlayers() + "/" + currentLobby.getMaximumNumberOfPlayers());

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
        screen.setWidth(400);
        screen.setHeight(400);
        screen.setAlwaysOnTop(true);
        screen.initModality(Modality.APPLICATION_MODAL);
        screen.initStyle(StageStyle.UNDECORATED);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> screen.close());
        delay.play();

        screen.showAndWait();
      }
    });



  }

}
