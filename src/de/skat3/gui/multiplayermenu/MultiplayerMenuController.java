package de.skat3.gui.multiplayermenu;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class MultiplayerMenuController {

  @FXML
  public Button refreshButton;

  public MultiplayerMenuController() {

  }

  public void hostServer() {

  }

  public void directConnect() {
    TextInputDialog dialog = new TextInputDialog("");
    dialog.setTitle("Direct Connect");
    dialog.setHeaderText("Direct Connect");
    dialog.setContentText("Please enter Server IP:");
    dialog.initStyle(StageStyle.UTILITY);

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(ip -> System.out.println("IP: " + ip));
  }

  public void refresh() {

  }

  public void joinServer() {

  }

}
