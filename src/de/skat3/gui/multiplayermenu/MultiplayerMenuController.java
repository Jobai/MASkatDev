package de.skat3.gui.multiplayermenu;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
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
