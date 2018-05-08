package de.skat3.gui.singleplayermenu;

import de.skat3.main.SkatMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * 
 * @author tistraub
 *
 */
public class SingleplayerController {

  @FXML
  private AnchorPane root;
  @FXML
  private ComboBox<String> difficultyP1;
  @FXML
  private ComboBox<String> difficultyP2;
  @FXML
  private ChoiceBox<String> mode;
  @FXML
  private TextField modeValue;
  @FXML
  private Tooltip tooltipModeValue;
  @FXML
  private CheckBox kontraRekontra;
  @FXML
  private Button startButton;

  private AnchorPane main;
  private Pane singleplayer;


  @FXML
  public void initialize() {
    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    ObservableList<String> difficultyList = FXCollections.observableArrayList("Easy", "Hard");

    difficultyP1.setItems(difficultyList);
    difficultyP1.getSelectionModel().selectFirst();
    difficultyP2.setItems(difficultyList);
    difficultyP2.getSelectionModel().selectFirst();
    mode.setItems(modeList);
    mode.getSelectionModel().selectFirst();
    modeValue.setText(String.valueOf(48));
  }


  @FXML
  void switchMode(ActionEvent event) {
    if (mode.getSelectionModel().getSelectedItem() == "Seeger") {
      modeValue.setText(String.valueOf(48));
    } else {
      modeValue.setText(String.valueOf(-750));
    }
  }

  public void setPanes(AnchorPane main, Pane singleplayer) {
    this.main = main;
    this.singleplayer = singleplayer;
  }


  @FXML
  void close(ActionEvent event) {
    this.main.getChildren().remove(this.singleplayer);
  }

  @FXML
  void startGame(ActionEvent event) {

    boolean ai1Hard = false;
    boolean ai2Hard = false;
    int intScoreValue = 0;

    intScoreValue = Integer.parseInt(this.modeValue.getText());

    if (this.difficultyP1.getSelectionModel().getSelectedItem() == "Hard") {
      ai1Hard = true;
    }

    if (this.difficultyP2.getSelectionModel().getSelectedItem() == "Hard") {
      ai2Hard = true;
    }

    SkatMain.mainController.startSingleplayerGame(ai1Hard, ai2Hard, intScoreValue,
        this.kontraRekontra.isSelected());


  }

}
