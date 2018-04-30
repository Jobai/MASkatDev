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
    btnHost.disableProperty().bind(
        Bindings.isEmpty(serverName.textProperty()).or(Bindings.isEmpty(modeValue.textProperty())));

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

        int value = Integer.parseInt(modeValue.getText());

        System.out.println(mode.getSelectionModel().getSelectedItem());

        if (mode.getSelectionModel().getSelectedItem() == "Seeger") {
          System.out.println("in");
          if ((value % 3) == 0) {
            btnHost.setDisable(false);
          } else {
            btnHost.setDisable(true);
          }
        } else if (mode.getSelectionModel().getSelectedItem() == "Bierlachs") {
          if ((value >= -1000) && (value <= -500)) {
            btnHost.setDisable(false);
          } else {
            btnHost.setDisable(true);
          }
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
   * 
   * @param s current Stage
   */
  public void setStage(Stage s) {
    this.hostPopup = s;
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
      modeValue.setText("" + 48);
    } else if (mode.getValue() == "Bierlachs") {
      modeValue.setText("" + -750);
    }
  }

}
