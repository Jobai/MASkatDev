package de.skat3.gui.multiplayermenu;

import java.net.UnknownHostException;
import de.skat3.main.SkatMain;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HostPopupController {

  private Stage hostPopup;

  @FXML
  private TextField serverName;
  @FXML
  private TextField password;
  @FXML
  private TextField timer;
  @FXML
  private ChoiceBox<Integer> playerCount;
  @FXML
  private ChoiceBox<String> mode;
  @FXML
  private TextField modeValue;

  @FXML
  private void initialize() {
    ObservableList<Integer> playerList = FXCollections.observableArrayList(3, 4);
    playerCount.setItems(playerList);

    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    mode.setItems(modeList);
  }

  public HostPopupController() {

  }

  public void setStage(Stage s) {
    this.hostPopup = s;
  }

  public void hostGame() throws NumberFormatException, UnknownHostException {

    // public void hostMultiplayerGame(String name, int numberOfPlayers, int timer,
    // boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {
    // this.hostMultiplayerGame(name, "", numberOfPlayers, timer, kontraRekontraEnabled,
    // scoringMode);
    //
    // }
    //
    // public void hostMultiplayerGame(String name, String password, int numberOfPlayers, int timer,
    // boolean kontraRekontraEnabled, int scoringMode) throws UnknownHostException {



    int players = playerCount.getValue();
    int intTimer;
    if (timer.getText() == "") {
      intTimer = 0;
    } else {
      intTimer = Integer.parseInt(timer.getText());
    }

    if (password.getText() == "") {
      SkatMain.mainController.hostMultiplayerGame(serverName.getText(), players, intTimer, false,
          Integer.parseInt(modeValue.getText()));
    } else {
      SkatMain.mainController.hostMultiplayerGame(serverName.getText(), password.getText(), players,
          intTimer, false, Integer.parseInt(modeValue.getText()));
    }

    hostPopup.close();
  }

  public void switchMode(ActionEvent e) {
    if (mode.getValue() == "Seeger") {
      modeValue.setText(String.valueOf(48));
    } else if (mode.getValue() == "Bierlachs") {
      modeValue.setText(String.valueOf(750));
    }
  }

}
