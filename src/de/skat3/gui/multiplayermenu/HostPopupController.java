package de.skat3.gui.multiplayermenu;

import de.skat3.main.SkatMain;
import java.net.UnknownHostException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
  private Button btnHost;
  @FXML
  private Tooltip tooltipModeValue;

  @FXML
  private void initialize() {
    System.out.println(mode.valueProperty());
    BooleanBinding b1 = Bindings.isEmpty(serverName.textProperty());
    BooleanBinding b2 = Bindings.isEmpty(modeValue.textProperty());
    BooleanBinding b3 = Bindings.equal("Seeger", mode.valueProperty());
    BooleanBinding b4 = Bindings.equal("Bierlachs", mode.valueProperty());

    // btnHost.disableProperty().bind(b1.or(b2).or(Bindings.when(b3).then(Bindings.d)));

    timer.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
          timer.setText(oldValue);
        }
      }
    });

    modeValue.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("-?([1-9][0-9]*)?")) {
          modeValue.setText(oldValue);
        }
      }
    });

    tooltipModeValue.setText(
        "Seeger: Value must divided by 3 \n " + "Bierlachs: Value must between -500 and -1000");

    ObservableList<Integer> playerList = FXCollections.observableArrayList(3, 4);
    playerCount.setItems(playerList);
    playerCount.getSelectionModel().selectFirst();

    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    mode.setItems(modeList);
    mode.getSelectionModel().selectFirst();

  }

  /**
   * Save current stage, which is needed to close them later.
   * 
   * @param s current Stage
   */
  public void setStage(Stage s) {
    this.hostPopup = s;
  }

  /**
   * Host a new game with the inserted values.
   * 
   * @throws NumberFormatException exception
   * @throws UnknownHostException exception
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
   * Handles action, if user change dropdown value.
   * 
   * @param e ActionEvent
   */
  public void switchMode(ActionEvent e) {
    if (mode.getValue() == "Seeger") {
      modeValue.setText("" + 48);
    } else if (mode.getValue() == "Bierlachs") {
      modeValue.setText("" + -750);
    }
  }

}
