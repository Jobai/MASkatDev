package de.skat3.gui.matchfield;

import de.skat3.main.SkatMain;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.util.Duration;

/**
 * View class of the Matchfield.
 * 
 * @author Aljoscha Domonell
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

  private void iniBackground() {
    try {
      URL url = Matchfield.class.getResource("DSC01542_1_orginal-3.jpg");
      Image background = new Image(url.openStream());
      ImageView iv = new ImageView(background);
      iv.fitHeightProperty().bind(this.root.widthProperty());
      iv.fitWidthProperty().bind(this.root.widthProperty());
      this.root.getChildren().add(iv);
      iv.toBack();
    } catch (Exception e) {
      e.printStackTrace();
    }

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

    this.iniBackground();
    // this.overlayController.bindChat();
  }

}
