package de.skat3.gui.matchfield;

import de.skat3.main.SkatMain;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * View class of the Matchfield.
 * 
 * @author adomonel
 *
 */
public class Matchfield {
  public static Duration animationTime = Duration.seconds(0.2);

  double sceneWidth;
  double sceneHeight;

  AnchorPane root;
  Scene scene;

  private InGameController controller;
  InGameOverlayController overlayController;
  InGameTableController tableController;
  InGameTableView tableView;

  /**
   * Returns a Matchfield.
   */
  public Matchfield() {
    sceneWidth = 1280;
    sceneHeight = 720;

    this.tableView = new InGameTableView(this);

    URL u = Matchfield.class.getResource("InGameOverlayView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.root = (AnchorPane) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.tableController = new InGameTableController(this.tableView);
    this.overlayController = loader.getController();
    this.controller = new InGameController(this);

    this.root.getChildren().add(this.tableView.tableScene);

    this.tableView.tableScene.toBack();

    this.scene = new Scene(this.root, sceneWidth, sceneHeight);

    this.iniComponents();
  }

  public InGameController getController() {
    return this.controller;
  }

  public Scene getScene() {
    return this.scene;
  }

  /**
   * Initializes all preset components of this Matchfield.
   */
  private void iniComponents() {

    this.tableView.iniComponents();

    this.overlayController.iniComponents();


    if (SkatMain.mainController.isHost) {
      this.overlayController.showStartButton();
    }

    // this.overlayController.bindChat();
  }

}
