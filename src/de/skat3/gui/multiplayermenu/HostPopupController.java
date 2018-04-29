package de.skat3.gui.multiplayermenu;

import de.skat3.main.SkatMain;
import java.net.UnknownHostException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
  private CheckBox kontraRekontra;

  @FXML
  private void initialize() {
    ObservableList<Integer> playerList = FXCollections.observableArrayList(3, 4);
    playerCount.setItems(playerList);

    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    mode.setItems(modeList);
  }

  public void setStage(Stage s) {
    this.hostPopup = s;
    //First option is preselect for convenience - JB 29.04.18
    playerCount.getSelectionModel().selectFirst();
    mode.getSelectionModel().selectFirst();
    
  }

  /**
   * .
   * 
   * @throws NumberFormatException TODO
   * @throws UnknownHostException TODO
   */
  public void hostGame() throws NumberFormatException, UnknownHostException {

    int players = playerCount.getValue();
    int intTimer;
    if (timer.getText().isEmpty()) {
      intTimer = 0;
    } else {
      intTimer = Integer.parseInt(timer.getText());
    }

    if (password.getText().isEmpty()) {
      SkatMain.mainController.hostMultiplayerGame(serverName.getText(), players, intTimer,
          kontraRekontra.isSelected(), Integer.parseInt(modeValue.getText()));
    } else {
      SkatMain.mainController.hostMultiplayerGame(serverName.getText(), password.getText(), players,
          intTimer, kontraRekontra.isSelected(), Integer.parseInt(modeValue.getText()));
    }

    hostPopup.close();
  }

  /**
   * . TODO
   * 
   * @param e Event
   */
  public void switchMode(ActionEvent e) {
    if (mode.getValue() == "Seeger") {
      modeValue.setText(String.valueOf(48));
    } else if (mode.getValue() == "Bierlachs") {
      modeValue.setText(String.valueOf(750));
    }
  }

}
