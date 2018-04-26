package de.skat3.gui.matchfield;

import java.io.IOException;
import java.net.URL;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Player;
import de.skat3.gui.menuframe.MenuFrame;
import de.skat3.main.SkatMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
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

    this.controller = new InGameController(this);
    this.tableController = new InGameTableController();
    this.tableView = new InGameTableView(this);


    URL u = Matchfield.class.getResource("InGameOverlayView.fxml");
    FXMLLoader loader = new FXMLLoader(u);
    try {
      this.root = (AnchorPane) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.overlayController = loader.getController();

    this.root.getChildren().add(this.tableView.tableScene);

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
    // this.overlayController.toFront();

    if (SkatMain.mainController.isHost) {
      this.overlayController.showStartButton();
    }
  }

}
