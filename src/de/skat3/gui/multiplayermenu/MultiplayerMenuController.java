package de.skat3.gui.multiplayermenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
  private Button joinButton;
  @FXML
  private Label serverIP;
  @FXML
  private Label serverPW;
  @FXML
  private AnchorPane root;
  @FXML
  private ImageView imageViewPwLock;

  Lobby currentLobby;
  ArrayList<Lobby> hostList = new ArrayList<Lobby>();


  private static class HostCell extends ListCell<String> {
    HBox hbox = new HBox();
    Image serverImage = new Image("guifiles/AppIcon.png");
    Image passwordImage = new Image("guifiles/lock.png");
    Image timerImage = new Image("guifiles/alarm-clock.png");
    Label timerTime = new Label();
    ImageView ivServer = new ImageView(serverImage);
    ImageView ivPassword = new ImageView(passwordImage);
    ImageView ivTimer = new ImageView(timerImage);
    Pane pane = new Pane();
    Label label = new Label();

    public HostCell(boolean password, boolean timer, String time) {
      super();
      ivServer.setFitWidth(30);
      ivServer.setFitHeight(30);
      ivTimer.setFitHeight(22);
      ivTimer.setFitWidth(22);
      hbox.setPrefHeight(50);
      hbox.setAlignment(Pos.CENTER_LEFT);
      hbox.setSpacing(20);
      label.setFont(new Font(20));
      timerTime.setFont(new Font(15));

      if (timer) {
        timerTime.setText(time + " sec");
      } else {
        ivTimer.setVisible(false);
      }

      if (!password) {
        ivPassword.setVisible(false);
      }

      hbox.getChildren().addAll(ivServer, label, ivPassword, ivTimer, timerTime, pane);
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

  public void startGame() {
    SkatMain.mainController.startGame();
  }

  /**
   * .
   */
  public void fillHostList() {

    ObservableList<String> items = FXCollections.observableArrayList();
    items.clear();
    hostList.clear();
    hostListView.setItems(items);

    Service<String> playService;
    class PlayService extends Service<String> {
      @Override
      protected Task<String> createTask() {
        return new Task<String>() {
          @Override
          protected String call() {

            hostList = SkatMain.mainController.getLocalHosts();
            for (Lobby lobby : hostList) {
              hostListView.setCellFactory(param -> new HostCell(lobby.isHasPassword(), true, "12"));
              // HostCell param = hostListView.setCellFactory(new HostCell(lobby.isHasPassword(),
              // true, "12"));

              items.add(lobby.getName() + " (" + lobby.lobbyPlayer + "/"
                  + lobby.getMaximumNumberOfPlayers() + " players)");
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
    stage.setResizable(false);
    stage.setTitle("Host Server");
    stage.setScene(new Scene(root));

    HostPopupController hostController = fxmlLoader.getController();
    hostController.setStage(stage);
    stage.show();

  }

  /**
   * Opens a popup with 2 input fields (ip and password) and connects to a server
   */
  public void directConnect() {

    Dialog<HashMap<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Direct Connect");
    dialog.setHeaderText("Direct Connect");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    // Set the button types.
    ButtonType startGame = new ButtonType("Start", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(startGame, ButtonType.CANCEL);

    TextField ip = new TextField();
    TextField pw = new TextField();

    grid.add(new Label("IP: "), 0, 0);
    grid.add(ip, 1, 0);
    grid.add(new Label("Password (optional)"), 0, 1);
    grid.add(pw, 1, 1);

    dialog.getDialogPane().setContent(grid);

    final Button okButton = (Button) dialog.getDialogPane().lookupButton(startGame);
    ip.textProperty().addListener((observable, oldValue, newValue) -> {
      okButton.setDisable(ip.getText().isEmpty());
    });

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == startGame) {
        HashMap<String, String> result = new HashMap<>();
        result.put("ip", ip.getText());
        result.put("pw", pw.getText());
        return result;
      }
      return null;
    });

    dialog.showAndWait().ifPresent(result -> {
      String sIp = result.get("ip");
      String sPw = result.get("pw");

      if (sPw.isEmpty()) {
        SkatMain.mainController.directConnectMultiplayerGame(sIp);
      } else {
        SkatMain.mainController.directConnectMultiplayerGame(sIp, sPw);
      }

    });


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
    joinButton.setDisable(true);
    if (!currentLobby.isHasPassword()) {
      SkatMain.mainController.joinMultiplayerGame(currentLobby);
    } else {
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

    joinButton.setDisable(false);

    if (currentLobby.isHasPassword()) {
      imageViewPwLock.setImage(
          new Image(getClass().getResourceAsStream("../../../../guifiles/lock-7-xxl.png")));
    } else {
      imageViewPwLock.setImage(
          new Image(getClass().getResourceAsStream("../../../../guifiles/lock-unlocked-xxl.png")));
    }

    // fill view fields
    serverName.setText(currentLobby.getName());
    serverIP.setText(currentLobby.getIp().toString());
    // serverPW.setText(currentLobby.);
    serverPlayer.setText(currentLobby.lobbyPlayer + "/" + currentLobby.getMaximumNumberOfPlayers());

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

    refreshButton.setDisable(true);

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
    pause.setOnFinished(event -> {
      root.getChildren().remove(p);
      refreshButton.setDisable(false);
    });
    pause.play();

  }

}
