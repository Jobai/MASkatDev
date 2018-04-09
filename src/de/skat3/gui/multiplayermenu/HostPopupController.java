package de.skat3.gui.multiplayermenu;

import de.skat3.main.SkatMain;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HostPopupController {

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
    ObservableList<Integer> playerList = FXCollections.observableArrayList(1, 2, 3);
    playerCount.setItems(playerList);
    
    ObservableList<String> modeList = FXCollections.observableArrayList("Seeger", "Bierlachs");
    mode.setItems(modeList);
  }
  
  public HostPopupController() {

  }
  
  public void hostGame() {
    SkatMain.mainController.hostMultiplayerGame(3, 0, "",false,48);
    System.out.println(playerCount.getValue());
  }
  
  
}
